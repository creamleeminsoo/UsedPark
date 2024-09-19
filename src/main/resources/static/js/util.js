
function getCookie(key) {
    let result = null;
    let cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        let dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

function httpRequest(method, url, body, success, fail) {
    let options = {
        method: method,
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
        },
        body: body,
    };

   if (method !== 'GET' && !(body instanceof FormData)) {
        options.headers['Content-Type'] = 'application/json';
        options.body = JSON.stringify(body);
    }

    fetch(url, options)
        .then(response => {
            if (response.status === 200 || response.status === 201) {
                return success();
            }
            const refresh_token = getCookie('refresh_token');
            if (response.status === 401 && refresh_token) {
                fetch('/api/token', {
                    method: 'POST',
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        refreshToken: getCookie('refresh_token'),
                    }),
                })
                    .then(res => {
                        if (res.ok) {
                            return res.json();
                        }
                    })
                    .then(result => {
                        localStorage.setItem('access_token', result.accessToken);
                        httpRequest(method, url, body, success, fail);
                    })
                    .catch(error => fail());
            } else {
                return fail();
            }
        });
}

function CommentHttpRequest(method, url, body, success, fail) {
    let options = {
        method: method,
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
        },
        body: body,
    };

    if (!(body instanceof FormData)) {
        options.headers['Content-Type'] = 'application/json';

    }

    fetch(url, options)
        .then(response => {
            if (response.status === 200 || response.status === 201) {
                return success();
            }
            const refresh_token = getCookie('refresh_token');
            if (response.status === 401 && refresh_token) {
                fetch('/api/token', {
                    method: 'POST',
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        refreshToken: getCookie('refresh_token'),
                    }),
                })
                    .then(res => {
                        if (res.ok) {
                            return res.json();
                        }
                    })
                    .then(result => {
                        localStorage.setItem('access_token', result.accessToken);
                        httpRequest(method, url, body, success, fail);
                    })
                    .catch(error => fail());
            } else {
                return fail();
            }
        });
}

function httpGetRequest(method, url, success, fail) {
    let options = {
        method: method,
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
        }
    };

    fetch(url, options)
        .then(response => {
            if (response.ok) {
                return response.text();
            } else if (response.status === 401) {
                alert('인증이 되지않았습니다');
                window.location.href = '/login';
            } else {
                throw new Error('에러 ' + response.status);
                window.location.href = '/login';
            }
        })
        .then(data => {
            document.open();
            document.write(data);
            document.close();
        })
        .catch(error => fail(error));
}
function success(data) {
         document.open();
         document.write(data);
         document.close();
}

function fail(error) {
         alert('접근 실패');
         console.error('Error:', error);
}

function httpRequestWithAuth(method, url, body, success, fail) {
    let options = {
        method: method,
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
        },
        body: body,
    };

    if (method !== 'GET' && !(body instanceof FormData)) {
        options.headers['Content-Type'] = 'application/json';
        options.body = JSON.stringify(body);
    }

    fetch(url, options)
        .then(response => {
            if (response.status === 200 || response.status === 201) {
                return response.json().then(data => success(data));
            }

            const refresh_token = getCookie('refresh_token');
            if (response.status === 401 && refresh_token) {
                return fetch('/api/token', {
                    method: 'POST',
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        refreshToken: getCookie('refresh_token'),
                    }),
                })
                .then(res => {
                    if (res.status === 200 || res.status === 201) {
                        return res.json();
                    } else {
                        throw new Error('Failed to refresh token');
                    }
                })
                .then(result => {
                    localStorage.setItem('access_token', result.accessToken);

                    return httpRequestWithAuth(method, url, body, success, fail);
                })
                .catch(() => {
                    alert('세션이 만료되었습니다. 다시 로그인해주세요.');
                    fail();
                });
            } else {
                return fail();
            }
        })
        .catch(() => fail());
}






