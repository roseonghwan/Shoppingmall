<h1> Mobile Shopping Mall </h1>

<h3> 첫번째 화면 </h3>
<br>

![home](https://user-images.githubusercontent.com/28584275/96878494-e47d8700-14b5-11eb-8ff8-8bcb6c3006d9.png)

<li> 리사이클러뷰를 활용해 카드뷰로 각 품목을 나타내었습니다. </li>
<br>
  
![realtime](https://user-images.githubusercontent.com/28584275/96882444-45a75980-14ba-11eb-932a-67019b72cd18.png)

<li> 위의 리사이클러뷰의 아이템들은 firebase의 realtime database를 이용해 이미지, 가격, 이름을 가져왔습니다. </li>
<br>

![hidden](https://user-images.githubusercontent.com/28584275/96883973-f4986500-14bb-11eb-9984-c203d734409d.png)

<li> 화면에 나온 품목을 클릭하게 되면 아래에 없던 장바구니와 결제로 이어지는 버튼이 생성됩니다. </li>
<br>
<hr>

<h3> 두번째 화면 </h3>
<br>

![basket](https://user-images.githubusercontent.com/28584275/96885020-23fba180-14bd-11eb-846d-aa57e39ecff0.png)

<li> 첫번째 화면에서 품목을 클릭하고 장바구니 버튼을 누르면 두번째 화면으로 이동하면서 클릭한 품목이 이동되었습니다. </li>
<li> 최종적으로 결제할 물품을 선택할 수 있도록 체크박스를 추가했습니다. </li>
<br>

![firestore_basket](https://user-images.githubusercontent.com/28584275/96885943-0ed34280-14be-11eb-9ea7-dcadb8f99f4c.png)

<li> 첫번째 화면에서 품목을 클릭하면 해당 품목을 firebase의 firestore의 basekt에 하나씩 저장하고, 장바구니에서는 저장되어 있는 것들을 가져왔습니다. </li>
<br>
<hr>

<h3> 세번째 화면 </h3>
<br>

![payment](https://user-images.githubusercontent.com/28584275/96886316-6e315280-14be-11eb-9ba2-ca2644e8751e.png)

<li> 두번째 화면에서 체크박스가 체크되어 있는 상태에서 구매버튼을 누르면 세번째 화면으로 이동하면서 체크된 품목들만 이동되었습니다. </li>
<br>

![pay_firestore](https://user-images.githubusercontent.com/28584275/96887077-2d860900-14bf-11eb-9861-73bda30ca696.png)

<li> 세번째 화면 또한 두번째 화면으로의 이동과 마찬가지로 firestore의 payment에 저장하고, 가져오는 식으로 구현하였습니다.
<br>

![final](https://user-images.githubusercontent.com/28584275/96886682-c8caae80-14be-11eb-8610-bb92b745a8ce.png)

<li> 마지막 결제 화면에서 주소와 연락처를 입력하지 않으면 토스트문으로 띄우게 하였고, 결제완료 버튼을 누르면 다시 첫번째 화면으로 이동하였습니다. </li>
<br>

![pay_direct](https://user-images.githubusercontent.com/28584275/96887542-a5543380-14bf-11eb-9a53-370057a5920c.png)
<li> 추가적으로 첫번째 화면에서 품목 선택 후 바로 구매 페이지인 세번째 화면으로 이동할 경우에는 그 품목만 결제할 수 있도록 구현하였습니다. </li>
<br>
<hr>
