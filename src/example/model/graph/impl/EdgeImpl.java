/**
 */
package example.model.graph.impl;

import example.model.graph.Edge;
import example.model.graph.GraphPackage;
import example.model.graph.Vertice;

import fr.inria.atlanmod.neoemf.core.DefaultPersistentEObject;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link example.model.graph.impl.EdgeImpl#getFrom <em>From</em>}</li>
 *   <li>{@link example.model.graph.impl.EdgeImpl#getTo <em>To</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeImpl extends DefaultPersistentEObject implements Edge {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GraphPackage.Literals.EDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vertice getFrom() {
		return (Vertice)eGet(GraphPackage.Literals.EDGE__FROM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrom(Vertice newFrom) {
		eSet(GraphPackage.Literals.EDGE__FROM, newFrom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vertice getTo() {
		return (Vertice)eGet(GraphPackage.Literals.EDGE__TO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(Vertice newTo) {
		eSet(GraphPackage.Literals.EDGE__TO, newTo);
	}

} //EdgeImpl
