import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
// Снова добавим необходимые библиотеки для работы

public class Cryptography {
    // Создали новый класс Криптография, и в методе main запросим от пользователя
    //ввести ключевое слово, которое сразу преобразуем, как и в первом задании
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ключевое слово: ");
        String key = scanner.nextLine().toLowerCase();
        List<Character> keyList = new ArrayList<>();
        //добавим посимвольно в список нового ключа данные из введеного ключа
        for (char c : key.toCharArray()) {
            keyList.add(c);
        }
        //отсортируем данные нового ключа в новую переменную, а также преобразуем ключ в цифры, в порядке русского алфавита
        List<Character> sortedKey = new ArrayList<>(keyList);
        sortedKey.sort(Character::compare);
        List<Integer> order = new ArrayList<>();
        //В новую переменную посимвольно добавим данный цифровой ключ
        for (char c : keyList) {
            int index = sortedKey.indexOf(c);
            order.add(index + 1);
            sortedKey.set(index, '1');
        }
        // Запросим у пользователя ввести текст для шифрования и преобразуем его также, как и в первом задании
        System.out.print("Введите исходный текст: ");
        String textInput = scanner.nextLine().toLowerCase();
        textInput = textInput.replaceAll("[^а-я]", "");
        //Введем новую целочисленную переменную для определения: делится ли длина преобразованной строки с длинной ключа
        //если нет - добавим новый символ, который довольно редко используется в русском языке - ь
        //добавление символа будет происходит, пока длина фразы и ключа не будет делиться без остатка
        int num = textInput.length() % order.size();
        if (num != 0) {
            int count = order.size() - num;
            textInput += String.valueOf('ь').repeat(count);
        }
        // вводим словарь, в котором ключом будет являться позиция строки
        HashMap<Integer, String> dictForCrypt = new HashMap<>();
        for (int i = 0; i < order.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < textInput.length(); j += order.size()) {
                sb.append(textInput.charAt(j));
            }
            dictForCrypt.put(order.get(i), sb.toString());
        }
        //Выведем полученный промежуточный результат
        System.out.println(dictForCrypt);
        // введем новую переменную, в которую и будем записывать окончательный результат шифрования
        StringBuilder cryptText = new StringBuilder();
        for (int i = 1; i <= order.size(); i++) {
            cryptText.append(dictForCrypt.get(i));
        }
        //Действия аналогичны первому заданию, делим строку из словаря по 5 символов и разделяем пробелом
        StringBuilder crypText = new StringBuilder();
        int count = 0;
        for (int i = 0; i < cryptText.length(); i++) {
            if (count != 5) {
                crypText.append(cryptText.charAt(i));
                count++;
            } else {
                crypText.append(' ');
                crypText.append(cryptText.charAt(i));
                count = 1;
            }
        }
        //Выведем полученный результат шифрования
        System.out.println();
        System.out.println("Зашифрованный текст:");
        System.out.println(crypText);
        System.out.println();
        //Попросим пользователя ввести зашифрованный текст, допустим текст будет не изначальным
        //Оператор закодировал несколько текстов, и теперь ему необходимо раскодировать новый текст
        // полученный от другого пользователя
        System.out.print("Введите зашифрованный текст: ");
        //Аналогичным образом убираем пробелы
        String encryptedText = scanner.nextLine().replace(" ", "");
        //Определяем длину текста
        int length = encryptedText.length() / order.size();
        //Вводим новый словарь, в который посимвольно добавляем необходимые данные
        HashMap<Integer, String> encryptedDict = new HashMap<>();
        for (int i = 0; i < order.size(); i++) {
            encryptedDict.put(i + 1, encryptedText.substring(length * i, length * (i + 1)));
        }
        //Вводим новую строку, добавляем посимвольно текст из прошлого словаря
        StringBuilder decrypText = new StringBuilder();
        for (int i = 0; i < length; i++) {
            for (int num1 : order) {
                decrypText.append(encryptedDict.get(num1).charAt(i));
            }
        }
        //Выводим результат
        System.out.println();
        System.out.println("Дешифрованный текст:");
        System.out.println(decrypText);
    }
}