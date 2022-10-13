package ru.dns.managers;


import ru.dns.pages.*;

/**
 * Класс для управления страничками
 * @author Алехнович Александр
 */
public class PageManager {

    /**
     * Менеджер страничек
     * @author Алехнович Александр
     */
    private static PageManager pageManager;

    /**
     * Страница с результатами поиска
     * @author Алехнович Александр
     */
    private SearсhResultsPage afterSearсhPage;

    /**
     * Стартовая страничка
     * @author Алехнович Александр
     */
    private StartSearchPage startSearchPage;

    /**
     * Страница карточки товара
     * @author Алехнович Александр
     */
    private CardProductPage cardProductPage;

    /**
     * Страница корзины
     * @author Алехнович Александр
     */
    private BasketPage basketPage;

    /**
     * Базовый класс всех страничек
     * @author Алехнович Александр
     */
    private BasePage basePage;

    /**
     * Конструктор специально был объявлен как private (singleton паттерн)
     * @author Алехнович Александр
     * @see PageManager#getPageManager()
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация PageManager
     * @author Алехнович Александр
     * @return PageManager
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Ленивая инициализация {@link SearсhResultsPage}
     * @author Алехнович Александр
     * @return SearсhResultsPage
     */
    public SearсhResultsPage getSearсhResultsPage() {
        if (afterSearсhPage == null) {
            afterSearсhPage = new SearсhResultsPage();
        }
        return afterSearсhPage;
    }

    /**
     * Ленивая инициализация {@link StartSearchPage}
     * @author Алехнович Александр
     * @return StartSearchPage
     */
    public StartSearchPage getStartSearchPage() {
        if (startSearchPage == null) {
            startSearchPage = new StartSearchPage();
        }
        return startSearchPage;
    }

    /**
     * Ленивая инициализация {@link CardProductPage}
     * @author Алехнович Александр
     * @return CardProductPage
     */
    public CardProductPage getCardProductPage() {
        if (cardProductPage == null) {
            cardProductPage = new CardProductPage();
        }
        return cardProductPage;
    }

    /**
     * Ленивая инициализация {@link BasketPage}
     * @author Алехнович Александр
     * @return BasketPage
     */
    public BasketPage getBasketPage() {
        if (basketPage == null) {
            basketPage = new BasketPage();
        }
        return basketPage;
    }

    /**
     * Ленивая инициализация {@link BasketPage}
     * @author Алехнович Александр
     * @return BasketPage
     */
    public BasePage getBasePage() {
        if (basePage == null) {
            basePage = new BasePage();
        }
        return basePage;
    }

    /**
     * Присвоение null объекту {@link BasePage}
     * @author Алехнович Александр
     */
    public void getCloseBasePage() {
        basePage = null;
    }
}
