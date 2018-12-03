package example;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import example.model.graph.Edge;
import example.model.graph.Graph;
import example.model.graph.GraphFactory;
import example.model.graph.Vertice;
import fr.inria.atlanmod.neoemf.data.PersistenceBackendFactoryRegistry;
import fr.inria.atlanmod.neoemf.data.blueprints.BlueprintsPersistenceBackendFactory;
import fr.inria.atlanmod.neoemf.data.blueprints.neo4j.option.BlueprintsNeo4jOptionsBuilder;
import fr.inria.atlanmod.neoemf.data.blueprints.util.BlueprintsURI;
import fr.inria.atlanmod.neoemf.option.AbstractPersistenceOptionsBuilder;
import fr.inria.atlanmod.neoemf.option.InvalidOptionException;
import fr.inria.atlanmod.neoemf.resource.PersistentResourceFactory;
import reference.Reference;
import reference.ReferenceFactory;

public class ExampleTutorial {
	
	private ResourceSet resourceSet = new ResourceSetImpl();
	
	public ExampleTutorial() {
		
	}
	
	public static void main(String[] args) {
		ExampleTutorial tutorial = new ExampleTutorial();
		
		try {
			deleteFolder(new File("databases/myGraph.graphdb"));
			deleteFolder(new File("databases/reference.graphdb"));
			
			Resource graphResource = tutorial.createDatabase("databases/myGraph.graphdb");
			Resource referenceResource = tutorial.createDatabase("databases/reference.graphdb");
			
			Graph graphModel = tutorial.newGraphModel();
			Reference referenceModel = tutorial.newReferenceModel(graphModel);

			System.err.println(">> add graph");
			graphResource.getContents().add(graphModel);
			System.err.println(">> add reference");
			referenceResource.getContents().add(referenceModel);
			System.err.println(">> save graph");
			graphResource.save(AbstractPersistenceOptionsBuilder.noOption());
			System.err.println(">> save reference");
			referenceResource.save(AbstractPersistenceOptionsBuilder.noOption());
			
			//tutorial.write(graphResource, graphModel);
			//tutorial.write(referenceResource, referenceModel);
			
			referenceResource.unload();	
			graphResource.unload();	

			tutorial.printModel(tutorial.read(graphResource));
			//tutorial.printModel(tutorial.read(referenceResource));			
		} catch (InvalidOptionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private Resource createDatabase(String path) throws InvalidOptionException, IOException {
		System.err.println(">> create and save " + path);
		PersistenceBackendFactoryRegistry.register(BlueprintsURI.SCHEME,
	                BlueprintsPersistenceBackendFactory.getInstance());

	    resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap()
	               .put(BlueprintsURI.SCHEME, PersistentResourceFactory.getInstance());

	    Resource resource = resourceSet.createResource(BlueprintsURI.createFileURI(new File(
	                path)));
	        /*
	         * Specify that Neo4j is used as the underlying blueprints backend. Note
	         * using the BlueprintsNeo4jOptionsBuilder class to create the option
	         * map automatically sets Neo4j as the graph backend.
	         */
	    resource.save(BlueprintsNeo4jOptionsBuilder.newBuilder().asMap());
	     
	    return resource;
	}

	private Reference newReferenceModel(Graph graphModel) {
		System.err.println(">> new reference model ");

		ReferenceFactory factory = ReferenceFactory.eINSTANCE;
		Reference reference = factory.createReference();
			
		reference.setEdge(graphModel.getEdges().get(0));
		
		return reference;
	}
	
	private Graph newGraphModel() {
		System.err.println(">> new graph model ");

		GraphFactory factory = GraphFactory.eINSTANCE;
		Graph graph = factory.createGraph();

		for (int i = 0; i < 100; i++) {
		  Vertice v1 = factory.createVertice();
		  v1.setLabel("Vertice " + i + "a");
		  Vertice v2 = factory.createVertice();
		  v2.setLabel("Vertice " + i + "b");
		  Edge e = factory.createEdge();
		  e.setFrom(v1);
		  e.setTo(v2);
		  graph.getEdges().add(e);
		  graph.getVertices().add(v1);
		  graph.getVertices().add(v2);
		}
		
		return graph;
	}
	
	private void write(Resource resource, EObject model) throws IOException {
		System.err.println(">> write model " + resource.getURI().toFileString());

		resource.getContents().add(model);
		
		resource.save(AbstractPersistenceOptionsBuilder.noOption());
	}
	
	/**
     * Reads the content of the provided {@code resource} and print it in the
     * console.
     * 
     * @param resource
     *            the resource to read
     * @throws IOException
     *             if an error occurs when loading the resource
     */
    @SuppressWarnings("unchecked")
	public <T extends EObject> T read(Resource resource) throws IOException {
		System.err.println(">> read model " + resource.getURI().toFileString());

        resource.load(AbstractPersistenceOptionsBuilder.noOption());
        return (T) resource.getContents().get(0);
    }
    
    public <T extends EObject> void printModel(final T model) {
        System.out.println("Print Model");
        printModel(model, "");
    }

    @SuppressWarnings("unchecked")
    private <T extends EObject> void printModel(final T model, final String prefix) {
        System.out.printf("%sObject %s\n", prefix, model.eClass().getName());
        for (final EAttribute attribute : model.eClass().getEAllAttributes()) {
            System.out.printf("%s   %s = %s\n", prefix, attribute.getName(), model.eGet(attribute));
        }
        for (final EReference reference : model.eClass().getEAllReferences()) {
            final Object referencedObject = model.eGet(reference);
            if (referencedObject != null) {
                if (referencedObject instanceof EObject) {
                    printObject((EObject) referencedObject, reference, prefix);
                } else if (referencedObject instanceof EList) {
                    final EList<EObject> referencedList = (EList<EObject>) referencedObject;
                    if (reference.isContainment()) {
                        System.out.printf("%s   %s => [\n", prefix, reference.getName());
                        for (final EObject object : referencedList) {
                            if (object.eIsProxy()) {
                                System.out.printf("%s\n", ((BasicEObjectImpl) object).eProxyURI());
                            } else {
                                printModel(object, prefix + "   ");
                            }
                        }
                    } else {
                        System.out.printf("%s   %s --> [\n", prefix, reference.getName());
                        for (final EObject object : referencedList) {
                            if (object.eIsProxy()) {
                                System.out.printf("%s\n", ((BasicEObjectImpl) object).eProxyURI());
                            } else {
                                printAttributes(object, prefix + "   ");
                            }
                        }
                    }
                    System.out.printf("%s]\n", prefix);
                }

            } else {
                if (reference.isContainment()) {
                    System.out.printf("%s   %s => null\n", prefix, reference.getName());
                } else {
                    System.out.printf("%s   %s --> null\n", prefix, reference.getName());
                }
            }
        }
    }

    private void printObject(final EObject referencedObject, final EReference reference, final String prefix) {
        if (referencedObject.eIsProxy()) {
            System.out.printf("%s   %s => %s", prefix, reference.getName(),
                    ((BasicEObjectImpl) referencedObject).eProxyURI());
        } else {
            if (reference.isContainment()) {
                System.out.printf("%s   %s => \n", prefix, reference.getName());
                printModel(referencedObject, prefix + "   ");
            } else {
                System.out.printf("%s   %s --> \n", prefix, reference.getName());
                printAttributes(referencedObject, prefix + "   ");
            }
        }
    }

    private <T extends EObject> void printAttributes(final T model, final String prefix) {
        System.out.printf("%sObject %s\n", prefix, model.eClass().getName());
        for (final EAttribute attribute : model.eClass().getEAllAttributes()) {
            System.out.printf("%s   %s = %s\n", prefix, attribute.getName(), model.eGet(attribute));
        }
    }
    
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
}
