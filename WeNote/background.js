chrome.tabs.query({
        active: true,
        currentWindow: true
    }, function(tab) {
    localStorage.ctab=tab[0].url;
        console.log(localStorage.getItem('ctab'));
});