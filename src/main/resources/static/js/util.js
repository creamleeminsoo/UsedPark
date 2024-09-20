
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
            if (response.ok) {
                const contentType = response.headers.get("Content-Type");
                if (contentType !== null) {
                    return response.json().then(data => success(data));
                } else {
                    success();
                }
            }
            else if (response.status === 401 && getCookie('refresh_token')) {
                const refresh_token = getCookie('refresh_token');
                fetch('/api/token', {
                    method: 'POST',
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        refreshToken: refresh_token,
                    }),
                })
                    .then(res => {
                        if (res.ok) {
                            return res.json();
                        } else {
                            throw new Error('토큰 갱신 실패');
                        }
                    })
                    .then(result => {
                        localStorage.setItem('access_token', result.accessToken);
                        httpRequest(method, url, body, success, fail);
                    })
                    .catch(error => fail());
            }
            else {
                return fail();
            }
        })
        .catch(error => fail());
}

function httpGetRequest(method, url, gerRequestFail) {
    let options = {
        method: method,
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
        }
    };

    fetch(url, options)
        .then(response => {
            if (response.ok) {
                return response.text().then(data => {
                    document.open();
                    document.write(data);
                    document.close();
                });
            } else if (response.status === 401) {
                alert('인증이 되지 않았습니다');
                window.location.href = '/login';
            } else {
                throw new Error('에러 ' + response.status);
            }
        })
        .catch(error => gerRequestFail(error));
}


function gerRequestFail(error) {
         alert('접근 실패');
         console.error('Error:', error);
}









