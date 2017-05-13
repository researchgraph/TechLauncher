/* Ref: https://github.com/gephi/gephi/wiki/How-to-code-with-the-Toolkit */

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.gephi.appearance.api.AppearanceController;
import org.gephi.appearance.api.AppearanceModel;
import org.gephi.appearance.api.Function;
import org.gephi.appearance.plugin.RankingElementColorTransformer;
import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.Query;
import org.gephi.filters.api.Range;
import org.gephi.filters.plugin.graph.DegreeRangeBuilder.DegreeRangeFilter;
import org.gephi.filters.plugin.graph.GiantComponentBuilder.GiantComponentFilter;
import org.gephi.filters.plugin.graph.KCoreBuilder.KCoreFilter;
import org.gephi.filters.plugin.operator.INTERSECTIONBuilder;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDirectionDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.GraphDistance;
import org.openide.util.Lookup;

public class HeadlessSimple {

    public void script() {

        //Initialization - create ProjectController
        ProjectController projectController = Lookup.getDefault().lookup(ProjectController.class);
        projectController.newProject();
        Workspace workspace = projectController.getCurrentWorkspace();

        //Get models and controllers for this new workspace - will be useful later
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
        PreviewModel model = Lookup.getDefault().lookup(PreviewController.class).getModel();
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        FilterController filterController = Lookup.getDefault().lookup(FilterController.class);
        AppearanceController appearanceController = Lookup.getDefault().lookup(AppearanceController.class);
        AppearanceModel appearanceModel = appearanceController.getModel();

        //Import file
        Container container;
        try {
            //Define path to the graph file
            File file = new File(getClass().getResource("/orcid-100k.graphml").toURI());
            container = importController.importFile(file);
            container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);   //Force DIRECTED
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        //Append imported data to GraphAPI
        importController.process(container, new DefaultProcessor(), workspace);

        //See if graph is well imported
        DirectedGraph graph = graphModel.getDirectedGraph();
        System.out.println("Nodes: " + graph.getNodeCount());
        System.out.println("Edges: " + graph.getEdgeCount());


        // @Hao Apply filters
        INTERSECTIONBuilder.IntersectionOperator intersectionOperator = new INTERSECTIONBuilder.IntersectionOperator();

        //Giant Component Filter
        GiantComponentFilter gcFilter = new GiantComponentFilter();
        gcFilter.init(graph);
        Query query1 = filterController.createQuery(gcFilter);

        //K-Core Filter
        KCoreFilter kcoreFilter = new KCoreFilter();
        kcoreFilter.filter(graph);
        kcoreFilter.setK(4);
        Query query2 = filterController.createQuery(kcoreFilter);

        //Degree Range Filter
        DegreeRangeFilter degreeFilter = new DegreeRangeFilter();
        degreeFilter.init(graph);
        degreeFilter.setRange(new Range(3, Integer.MAX_VALUE));
        Query query3 = filterController.createQuery(degreeFilter);

        //Combine queries
        Query combinedQuery = filterController.createQuery(intersectionOperator);
        filterController.setSubQuery(combinedQuery, query1);
        filterController.setSubQuery(combinedQuery, query2);

        GraphView view = filterController.filter(combinedQuery);

        //Set the filter result as the visible view
        graphModel.setVisibleView(view);


        //Export Status
        UndirectedGraph graphVisible = graphModel.getUndirectedGraphVisible();
        System.out.println("----- After Filter -----");
        System.out.println("Nodes: " + graphVisible.getNodeCount());
        System.out.println("Edges: " + graphVisible.getEdgeCount());

        //Run YifanHuLayout - The layout always takes the current visible view
        System.out.println("----- Run Layout -----");
        YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
        layout.setGraphModel(graphModel);
        layout.resetPropertiesValues();
        layout.setOptimalDistance(200f);
        layout.initAlgo();

        for (int i = 0; i < 200 && layout.canAlgo(); i++) {
            layout.goAlgo();
        }

        layout.endAlgo();
        System.out.println("----- End Layout -----");

        //Get Centrality
        GraphDistance distance = new GraphDistance();
        distance.setDirected(true);
        distance.execute(graphModel);

        //Define Output Preview
        model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.FALSE);
        model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.GREEN));
        model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.1f));
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(8));

        //Export file
        ExportController ec = Lookup.getDefault().lookup(ExportController.class);
        try {
            //Define Export file name
            ec.exportFile(new File("orcid.pdf"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }
}