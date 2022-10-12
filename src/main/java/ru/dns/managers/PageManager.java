package ru.dns.managers;


import ru.dns.pages.*;

/**
 * Класс для управления страничками
 */
public class PageManager {

    /**
     * Менеджер страничек
     */
    private static PageManager pageManager;


    private SearсhResultsPage afterSearсhPage;


    private StartSearchPage startSearchPage;


    private CardProductPage cardProductPage;


    private BasketPage basketPage;

    private BasePage basePage;

    /**
     * Конструктор специально был объявлен как private (singleton паттерн)
     *
     * @see PageManager#getPageManager()
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация PageManager
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     * @return BasketPage
     */
    public BasePage getBasePage() {
        if (basePage == null) {
            basePage = new BasePage();
        }
        return basePage;
    }
}
