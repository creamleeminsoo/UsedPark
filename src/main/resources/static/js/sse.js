function initializeSSE() {
    const accessToken = localStorage.getItem('access_token');

    if (!accessToken) {
        console.error("토큰이 없으므로 sse연결실패");
        return;
    }
    const eventSource = new EventSource(`/subscribe/${accessToken}`);

    eventSource.addEventListener('comment', function(event) {
        console.log("New comment: ", event.data);
        handleNotification(event.data,'comment');
    });

    eventSource.addEventListener('like', function(event) {
        console.log("New like: ", event.data);
        handleNotification(event.data,'like');
    });

    eventSource.addEventListener('chat', function(event) {
        console.log("New chat: ", event.data);
        handleNotification(event.data,'chat');
    });

    eventSource.onopen = function() {
        console.log("SSE connection opened");
    };

    eventSource.onerror = function(event) {
        handleError(event);
    };

    eventSource.onclose = function() {
        console.log("SSE connection closed");
    };
}

function handleError(error) {
    if (error.status === 401) {
        const refreshToken = getCookie('refresh_token');
        if (refreshToken) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: refreshToken,
                }),
            })
            .then(res => {
                if (res.ok) {
                    return res.json();
                } else {
                    throw new Error('Failed to refresh token');
                }
            })
            .then(result => {
                localStorage.setItem('access_token', result.accessToken);
                initializeSSE();
            })
            .catch(err => {
                console.error('Failed to refresh token:', err);
            });
        } else {
            console.error('No refresh token available.');
        }
    } else {
        console.error('Connection failed:', error);
    }
}


function handleNotification(data, type) {
    const parsedData = JSON.parse(data);
    const notificationContainer = document.getElementById('notifications');
    let notificationMessage = '';

    function formatTime(dateString) {
            const date = new Date(dateString);
            date.setHours(date.getHours() + 9);

            return date.toLocaleString('ko-KR', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit',
            });
        }

    if (type === 'chat') {
        const formattedTime = formatTime(parsedData.senderTime);
        const url = '/chat/${parsedData.roomId}';
        notificationMessage = `<div class="notification chat">
            <span class="chat-label">(채팅)</span> 회원님에게 새로운 채팅이 왔습니다! <a href="#" onclick="ChatGetRequest('/chat/${parsedData.roomId}', gerRequestFail)">보러가기</a>
            <div class="notification-time">${formattedTime}</div>
            <button class="delete-button" onclick="deleteNotification(this)">삭제</button>
        </div>`;
    } else if (type === 'comment') {
        const formattedTime = formatTime(parsedData.createdAt);
        notificationMessage = `<div class="notification comment">
            <span class="comment-label">(댓글)</span> 회원님의 글에 댓글이 달렸습니다! <a href="/board/${parsedData.boardId}/post/${parsedData.postId}">보러가기</a>
            <div class="notification-time">${formattedTime}</div>
            <button class="delete-button" onclick="deleteNotification(this)">삭제</button>
        </div>`;
    } else if (type === 'like') {
        const formattedTime = formatTime(parsedData.createdAt);
        notificationMessage = `<div class="notification like">
            <span class="like-label">(좋아요)</span> 회원님의 글에 좋아요가 달렸습니다! <a href="/board/${parsedData.boardId}/post/${parsedData.postId}">보러가기</a>
            <div class="notification-time">${formattedTime}</div>
            <button class="delete-button" onclick="deleteNotification(this)">삭제</button>
        </div>`;
    }

    notificationContainer.insertAdjacentHTML('afterbegin', notificationMessage);
    notificationCount++;
        const notificationCounter = document.getElementById("notificationCounter");
        notificationCounter.innerText = notificationCount;
        notificationCounter.style.display = "inline";
}




function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function deleteNotification(button) {
    const notification = button.parentElement;
    notification.remove();
    notificationCount--;
    const notificationCounter = document.getElementById("notificationCounter");
    notificationCounter.innerText = notificationCount;

    if (notificationCount <= 0) {
        notificationCounter.style.display = "none";
    }
}

function ChatGetRequest(url, gerRequestFail) {
    let options = {
        method: 'GET',
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
            } else if (response.status === 401 && getCookie('refresh_token')) {
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
                    return httpGetRequest(method, url, gerRequestFail);
                })
                .catch(error => {
                    alert('토큰 갱신 중 에러 발생');
                    gerRequestFail(error);
                });
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