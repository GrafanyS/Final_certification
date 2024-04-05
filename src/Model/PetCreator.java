package Model;

public class PetCreator extends Creator {

    @Override
    protected Pet createNewPet(PetType type) {
        Pet pet;
        switch (type) {
            case Cat:
                pet = new Cat();
                break;
            case Dog:
                pet = new Dog();
                break;
            case Hamster:
                pet = new Hamster();
                break;
            default:
                pet = null;
        }
        return pet;
    }
}
