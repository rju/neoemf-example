/**
 */
package reference;

import example.model.graph.Edge;

import fr.inria.atlanmod.neoemf.core.PersistentEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link reference.Reference#getEdge <em>Edge</em>}</li>
 * </ul>
 *
 * @see reference.ReferencePackage#getReference()
 * @model
 * @extends PersistentEObject
 * @generated
 */
public interface Reference extends PersistentEObject {
	/**
	 * Returns the value of the '<em><b>Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edge</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edge</em>' reference.
	 * @see #setEdge(Edge)
	 * @see reference.ReferencePackage#getReference_Edge()
	 * @model
	 * @generated
	 */
	Edge getEdge();

	/**
	 * Sets the value of the '{@link reference.Reference#getEdge <em>Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edge</em>' reference.
	 * @see #getEdge()
	 * @generated
	 */
	void setEdge(Edge value);

} // Reference
