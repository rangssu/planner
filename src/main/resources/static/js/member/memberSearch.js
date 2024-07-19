/* 친구추가 버튼 클릭 시 서밋하고 친구상태 불러오기 / 비동기로 버튼 비활성화 */
$(document).on("click", ".btn-add", function(){
	let button = $(this);
	let member_id = button.val();
	let csrfToken = $("meta[name='_csrf']").attr("content");
    let csrfHeader = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url		: "/friend/request",
		type	: "post",
		data	: {member_id : member_id},
		beforeSend: function(xhr) {
            // CSRF 토큰을 요청 헤더에 포함
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
		success	: function(status){
			if (status != null) {
				button.prop("disabled", true);
			}
		}
	});
});

/* 요청수락 버튼 클릭 시 서밋하고 친구상태 불러오기 / 비동기로 버튼 비활성화 */
$(document).on("click", ".btn-access", function(){
	let button = $(this);
	let member_id = button.val();
	let csrfToken = $("meta[name='_csrf']").attr("content");
    let csrfHeader = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		url		: "/friend/accept",
		type	: "post",
		data	: {member_id : member_id},
		beforeSend: function(xhr) {
            // CSRF 토큰을 요청 헤더에 포함
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
		success	: function(status){
			if (status != null) {
				button.prop("disabled", true);
			}
		}
	});
});

/* 키워드 검색 글자수 3글자 미만 알림창 띄우는 메서드 (input type="text" : maxlength 밖에 없음) */
const keywordCheck = () => {
	let keywordInput = document.getElementById('keywordInput');
	keywordInput.value = keywordInput.value.trim();
	if (keywordInput.value.length < 3) {
		Swal.fire({
		  icon: "error",
		  title: "warning",
		  text: "최소 3글자 이상 입력하세요.",
		});
		return false;
	}
	return true;
};

/* 친구인 경우 */
document.addEventListener('DOMContentLoaded', function() {
	const links = document.querySelectorAll('.submit-link');
	links.forEach(function(link) {
		link.addEventListener('click', function(event) {
			event.preventDefault();
			// 부모 폼이 있으면 submit
			const form = link.closest('form');
			if (form) {
				form.submit();
			}
		});
	});
});