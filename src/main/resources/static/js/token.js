function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

function storeAccessToken() {
    const token = getQueryParam('token');
    if (token) {
        localStorage.setItem('access_token', token);
    }
}


window.onload = storeAccessToken;