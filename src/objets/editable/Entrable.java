package objets.editable;

public interface Entrable {
	
	/**Renvoie le type de l'entrable
	 * 
	 * @return
	 */
	public TypeEntrable getTypeEntrable();
	
	/**Modifie l'instance de sorte � ce qu'elle se conforme � l'input.
	 * L'instance vit ensuite ind�pendemment des objets pass�s en input.
	 * 
	 * @param input
	 * @return
	 */
	public boolean conformerA(Object... input);
	
	
/*	*//**Verifie l'�galit� des types des deux Entrables
	 * 
	 * @param input
	 * @return
	 *//*
	public default boolean isConformableA(Entrable input) {
		return getTypeEntrable().equals(input.getTypeEntrable());
	}*/
}
