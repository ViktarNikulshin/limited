ymaps.ready(init);

function init() {
    // Создаем выпадающую панель с поисковыми подсказками и прикрепляем ее к HTML-элементу по его id.
    var suggestView1 = new ymaps.SuggestView('address', {boundedBy: [[56.060372, 23.687427], [51.450108, 32.828052]]});
}