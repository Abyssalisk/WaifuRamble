package game;

public class Character {

	public final String name;
	public int health;
	private final String meleeAttack;
	private final String rangedAttack;
	private final String defend;

	private final int meleeDamage;
	private int rangeDamage;
	public boolean isDead;

	public static void main(String[] args) {
		// using builder to create characters
		Character saber = new Character.Builder().name("Saber").health(100).meleeAttack("slash").defend("defend")
				.rangedAttack("slash wave").build();
		Character yuno = new Character.Builder().name("Yuno").health(100).meleeAttack("chop").defend("defend")
				.rangedAttack("throw axe").build();

		System.out.println(saber.toString());
		System.out.println(yuno.toString());
		System.out.println();

		System.out.println(saber.isDead ? "Saber is dead" : yuno.meleeAttack(yuno, saber));
		System.out.printf("%s Health: %d%n", saber.name, saber.health);

		System.out.println(saber.isDead ? "Saber is dead" : yuno.rangedAttack(saber));
		System.out.printf("%s Health: %d%n", saber.name, saber.health);

		System.out.println(saber.isDead ? "Saber is dead" : yuno.meleeAttack(yuno, saber));
		System.out.printf("%s Health: %d%n", saber.name, saber.health);

		System.out.println(saber.isDead ? "Saber is dead" : yuno.meleeAttack(yuno, saber));

	}

	private Character(Builder builder) {
		this.name = builder.name;
		this.health = builder.health;
		this.meleeAttack = builder.meleeAttack;
		this.rangedAttack = builder.rangedAttack;
		this.defend = builder.defend;
		this.meleeDamage = 25;
		this.rangeDamage = 50;
		this.isDead = false;
	}

	public int damage(Character character, int damageType) {
		character.health -= damageType;

		if (character.health <= 0)
			character.isDead = true;
		return character.health;
	}

	public String meleeAttack(Character characterAttacking, Character characterDamaged) {
		damage(characterDamaged, meleeDamage);
		return characterAttacking.name + " attacks " + characterDamaged.name;
	}

	public String rangedAttack(Character character) {
		damage(character, rangeDamage);
		return "range attacks " + character.name;
	}

	@Override
	public String toString() {
		return "Character [name=" + name + ", health=" + health + ", meleeAttack=" + meleeAttack + ", rangedAttack="
				+ rangedAttack + ", defend=" + defend + ", meleeDamage=" + meleeDamage + ", isDead=" + isDead + "]";
	}

	public static class Builder {
		private String name;
		private int health;
		private String meleeAttack;
		private String rangedAttack;
		private String defend;

		public Builder name(final String name) {
			this.name = name;
			return this;
		}

		public Builder health(final int health) {
			this.health = health;
			return this;
		}

		public Builder meleeAttack(final String meleeAttack) {
			this.meleeAttack = meleeAttack;
			return this;
		}

		public Builder rangedAttack(final String rangedAttack) {
			this.rangedAttack = rangedAttack;
			return this;
		}

		public Builder defend(final String defend) {
			this.defend = defend;
			return this;
		}

		public Character build() {
			return new Character(this);
		}

		@Override
		public String toString() {
			return "Builder [name=" + name + ", health=" + health + ", meleeAttack=" + meleeAttack + ", rangedAttack="
					+ rangedAttack + ", defend=" + defend + "]";
		}

	}

}
