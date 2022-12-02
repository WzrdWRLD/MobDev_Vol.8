package kz.talipovsn.rates;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// СОЗДАТЕЛЬ КОТИРОВОК ВАЛЮТ
public class RatesReader {

    private static final String BASE_URL = "https://www.iqair.com/ru/kazakhstan/pavlodar"; // Адрес с котировками

    // Парсинг котировок из формата html web-страницы банка, при ошибке доступа возвращаем null
    public static String getRatesData() {
        StringBuilder data = new StringBuilder();
        try {
            Document doc = Jsoup.connect(BASE_URL).timeout(5000).get(); // Создание документа JSOUP из html
            data.append("Уровень загрязнения атмосферы в городе Павлодар:\n"); // Считываем заголовок страницы
            data.append(String.format("%12s %12s\n", "Загрязнители", "Концентрация").trim());
            data.append("\n");
            Elements e = doc.select("div.aqi-overview-detail"); // Ищем в документе "<div class="exchange"> с данными о валютах
            Elements tables = e.select("tbody"); // Ищем таблицы с котировками
            Element table = tables.get(1); // Берем 1 таблицу
            int i = 0;
            // Цикл по строкам таблицы
            for (Element row : table.select("tr")) {
                // Цикл по столбцам таблицы
                for (Element col : row.select("td")) { //
                    data.append(String.format("%12s ", col.text())); // Считываем данные с ячейки таблицы
                }
                data.append("\n"); // Добавляем переход на следующую строку;
            }
            data.append("\n");
            data.append(String.format("%12s %12s %12s %12s %12s\n", "PM2.5 - это твердые микрочастицы " +
                            "и мельчайшие капельки жидкости (10 нм - 2,5 мкм в диаметре)\n",
                    "PM10 - это любые твердые частицы в воздухе диаметром 10 микрометров или меньше\n",
                    "NO2 - Оксид азота\n", "SO2 - Оксид серы\n", "CO - Монооксид углерода\n").trim());
        } catch (Exception ignored) {
            return null; // При ошибке доступа возвращаем null
        }
        return data.toString().trim(); // Возвращаем результат
    }

}