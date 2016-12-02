import org.gephi.project.api.ProjectController;
import org.openide.util.Lookup;
import org.gephi.project.api.Workspace;
import org.gephi.io.importer.api.Container;
import org.gephi.io.generator.plugin.RandomGraph;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.io.processor.plugin.AppendProcessor;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.generator.plugin.DynamicGraph;

public class gephi {
	ProjectController pc;
	Workspace workspace;
	GraphModel graphModel;
	UndirectedGraph undirectedGraph;

	public gephi(){
		pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		workspace = pc.getCurrentWorkspace();
		graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
		undirectedGraph = graphModel.getUndirectedGraph();
	}
	public void addNode(int[] list, int number){
		StringBuilder extremePoint = new StringBuilder();
		for(int i = 0; i< list.length; i++){
			if(i == list.length-1){
				extremePoint.append(list[i]);
				continue;
			}
			extremePoint.append(list[i] + ", ");
		}
		//Node newNode = graphModel.factory().newNode("n0");
	}
}
