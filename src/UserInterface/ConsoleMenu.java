package UserInterface;

import java.util.Scanner;
import Controller.*;
import Exceptions.IncorrectDataException;
import Model.PetType;

public class ConsoleMenu {

    PetController petController;

    public ConsoleMenu(PetController controller) {
        this.petController = controller;
    }

    public void start() {

        System.out.print("\033[H\033[J");
        try (Scanner in = new Scanner(System.in, "ibm866"); Counter count = new Counter()) {

            boolean flag = true;
            int id;
            while (flag) {
                System.out.println("1 - Список всех животных\n" +
                                   "2 - Завести новое животное\n" +
                                   "3 - Изменить данные о животном\n" +
                                   "4 - Что умеет животное\n" +
                                   "5 - Дрессировка\n" +
                                   "6 - Удалить запись\n" +
                                   "0 - Выход");
                String key = in.next();
                switch (key) {
                    case "1":
                        petController.getAllPet();
                        break;
                    case "2":
                        PetType type = menuChoice(in);
                        if (type != null) {
                            try {
                                petController.createPet(type);
                                count.add();
                                System.out.println("ОК");
                            } catch (IncorrectDataException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "3":
                        while (true) {
                            id = menuChoicePet(in);
                            if (id != 0)
                                try {
                                    petController.updatePet(id);
                                } catch (IncorrectDataException e) {
                                    System.out.println(e.getMessage());
                                }
                            else
                                break;
                        }
                        break;
                    case "4":
                        while (true) {
                            id = menuChoicePet(in);
                            if (id != 0)
                                petController.getCommands(id);
                            else
                                break;
                        }
                        break;
                    case "5":
                        id = menuChoicePet(in);
                        if (id != 0)
                            menuTrainPet(id, in);
                        break;
                    case "6":
                        id = menuChoicePet(in);
                        if (id != 0)
                            petController.delete(id);
                        break;
                    case "0":
                        flag = false;
                        break;
                    default:
                        System.out.println("такого варианта нет");
                }
            }
        }
    }
    private PetType menuChoice(Scanner in) {
        System.out.println("Какое животное добавить:\n" +
                           "1 - Кошка\n" +
                           "2 - Собака\n" +
                           "3 - Хомяк\n" +
                           "0 - Возврат в основное меню");

        while (true) {
            String key = in.next();
            switch (key) {
                case "1":
                    return PetType.Cat;
                case "2":
                    return PetType.Dog;                
                case "3":
                    return PetType.Hamster;                
                case "0":
                    return null;
                default: System.out.println("Такого варианта нет, введите число от 0 до 3");
            }
        }
    }

    private int menuChoicePet(Scanner in) {
        System.out.println("\nВведите номер животного, 0 для возврата в основное меню: ");
        while (true) {
            int id = in.nextInt();
            in.nextLine();
            if (id == 0)
                return id;
            if (petController.getById(id) == null) {
                System.out.println("Животного с таким номером нет, попробуйте еще раз, 0 для возврата в основное меню:");
            } else
                return id;

        }
    }

    private void menuTrainPet(int petId, Scanner in) {
        while (true) {
            System.out.println("Введите новую команду, 0 для возврата в основное меню: ");
            String command = in.nextLine();
            if (command.equals("0"))
                return;
            if (petController.trainPet(petId, command))
                System.out.println("получилось!");
        }
    }
}
