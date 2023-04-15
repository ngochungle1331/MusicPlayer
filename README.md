# AndroidConvention
Chung
-----

### Tại sao phải theo conventions?

-   Đó là sự thống nhất và được coi là biện pháp tốt nhất trong tất cả các dự án.
-   Không gây hại cho dự án nhưng mang lại hiểu quả cao hơn.
-   Mang lại sự chuyên nghiệp cho lập trình viên.
-   Hiệu quả cao nhất, trách mất thời gian khi làm việc nhóm, đặc biệt là nhóm lớn.
-   Thước đo đánh giá cho kỹ năng lập trình của lập trình viên.

### Ưu điểm

-   Phù hợp với hầu hết các devs ở công ty
-   Phù hợp với nhưng dự án hoặc những phần code mới từ đầu.

### Nhược điểm

-   Không phù hợp với những dự án, những phần đã làm từ trước
-   Không quen với những dev của một số công ty khác
-   Làm phiền bạn:



Mỗi dev có một sở thích cá nhân mạnh mẽ, có thể họ không muốn thay đổi để theo một cách khác ngay cả khi cách khác tốt hơn.

Những bạn mới thường có tư tưởng “hoàn thành” đồng nghĩa với “chạy được”, chỉ quan tâm bề ngoài ứng dụng chứ không quan tâm chất lượng source code.

Android coding conventions
------------------------------------

1.  Quy định về java

-   Viết hàm ngắn gọn

Cố gắng viết method ngắn và tập trung, điêu đó giúp giảm thiểu việc sử dụng lại các đoạn code nhiều lần không cần thiết.

-   Giữ cho dòng code ngắn

Cố gắng viết mỗi dòng code ngắn lại trừ trường hợp nhập text hoặc url hoặc những dòng lệnh bắt buộc phải cùng trên 1 dòng

-   Các classes bắt đầu với chữ viết HOA

``` java
public class SomeClass {}
```

-   Hằng số tất cả phải viết HOA

``` java
public static final String GOOGLE_HOME_URL = "http://www.google.com";
```

-   Tất cả các thành phần khác đều bắt đầu bằng chữ cái viết thường

Biến toàn cục, biến cục bộ, tham số hàm, package name, hàm...

-   Sử dụng 1 chữ cái viết hoa cho từ viết tắt, không viết hoa tất cả


Yes

``` java
private void getUrl(){}
```


No

``` java
private void getURL(){}
```

-   Sử dụng JavaDoc khi mới bắt đầu

Đừng chờ đợi đến khi hoàn thành mà hãy viết ngay khi bắt đầu class hoặc một hàm.

-   Sử dụng document khắp nơi trong dự án

``` java
/**
 * GET [/api/v1/student] Get student from service
 *
 * @param context  application Context
 * @param listener callBack listener
 */;
public static void getStudent(@NonNull Context context,
                              @NonNull final ApiClient.OnResponseListener<Student> listener) {}
```

-   Sử dụng document ở những nơi dùng public

Biến, hàm, biến toàn cục.

Chú ý: Cách viết Doc comments đọc ở đây [1]

-   Giới hạn phạm vi của biến nhỏ nhất có thể

Các biến cần được khai báo trong khối sâu nhất có thể. Ví dụ: một biến chỉ dùng trong điều kiện thì nên khai báo nó trong khối điều kiện.

``` java
if (...) {
    double d = someCalculation(…);
    doSomethingWith(d);
} else {
    // No use of d
}
```

-No

``` java
double d = 0;
if (…){…}else{…}
```

-   Luôn dùng ngoặc nhọn cho khối điều kiện if ngay cả khi chỉ có một dòng lệnh

-Yes

``` java
if (…) {
    doSomething();
}
```

-No

``` java
if (…)
    doSomething();
```

-Nếu trường hợp quá đặc biệt nếu muốn sử dụng thì tất cả phải đặt trên một dòng(Không khuyến khích điều này)

``` java
if (…) doSomething();
```

-   Sử dụng //TODO cho những nơi cần sửa đổi, bổ sung sau này


Ví dụ:

``` java
//TODO Remove after api finish
```

Trong android studio có thể mở lên cửa sổ danh sách các todo chưa làm, thói quen này giúp cho lập trình viên không bỏ sót các chức năng chưa hoàn thành.

-   Sử dụng dấu mở ngoặc nhọn { trên cùng dòng mệnh đề, không nằm riêng một dòng

-Yes

``` java
public void foo() {
    if (...) {
        doSomething();
    }
}
```

-No

``` java
public void foo()
{
    if (...)
    {
        doSomething();
    }
}
```

-   Sử dụng 4 khoảng trắng cho bắt đầu một khối code

-Yes

``` java
public void foo() {
    if (...) {
        doSomething();
    }
}
```

-No

``` java
public void foo() {
  if (...) {
      doSomething();
    }
}
```

• Sử dụng 8 khoảng trắng khi nhảy dòng -Yes

``` java
String s =
        somethingVeryLong(…);
```

-No

``` java
String s =
    somethingVeryLong(…);
```

### Quy ước file java

-   Sử dụng “m” đứng đầu cho biến non-public và non-static

-Yes

``` java
private String mFirstName;
private boolean mIsMarried;
```

-No

``` java
private String firstName;
private boolean isMarried;
```

-   Đặt 's' đứng đầu cho biến static(non-final)

-Yes

``` java
private static double sBiggestRadius;
```

-No

``` java
private static double biggestRadius;
```

-   Tránh sử dụng khối catch rỗng

Thường xuyên nhưng không phải luôn luôn. -Yes

``` java
try {
    …
} catch(SomeException se) {
    doSomethingReal();
}
```

-No

``` java
try {
    …
} catch(SomeException se) { }
```

-   Đừng catch exceprtion chung

-Yes

``` java
try {
    …
} catch(ExceptionType1 et1) {
    …
} catch(ExceptionType2 et2) {
    …
}
```

-No

``` java
try {
    …
} catch(Exception e) {
    …
}
```

### Quy ước XML

-   Đặt tên container:
* Acitivity: *_act
* Fragment: *_frag

-   Đặt tên views ID theo camel-case

ví dụ: btnLogin, tvCaption

-   Các prefix cho id trong XML

``` java
•   Button - btn
•   EditText - edt
•   TextView - tv
•   Checkbox - chk
•   RadioButton - rb
•   ToggleButton - tb
•   Spinner - spn
•   Menu - mnu
•   ListView - lv
•   GalleryView - gv
•   LinearLayout -ll
•   RelativeLayout – rl
•   Calendar ==> calendar
•   Datepicker => datePicker
•   Timepicker => timePicker
•   Videoview=> videoView
•   Seekbar==> seekBar
•   RatingBar==> ratingBar
•   Processbar => processBar
•   ImageView,==> img
•   ImageButton=> imgBtn
•   Layout parent => container
•   LayoutChild ==> ll,rl,fr
•   RecycleView =>recycleView
•   CardView =>cardView
•   ScrollView => scrollView
```

-   Đơn vị của chữ tùy theo yêu cầu của khách hàng mà dùng để sp hay dp
-   Cách đặt tên cho file XML

``` java
•   Item cho list view: *_item_list
•   Item cho grid view: *_item_grid
•   Custom cho view: *_custom
•   Custom cho dialog: *_dialog
```

Ví dụ:

``` xml
@+id/tvSubjectQuestions
@+id/ivTakeCamera
```

-   Đặt tên resource string, color, dimen

Yes
``` xml
<string name="network_error">Network error</string>
<string name="call_failed">Call failed</string>
<string name="map_failed">Map loading failed</string>
```

-No

``` xml
<string name="dialog.shake.title">Dialog title here</string>
<string name="edittext.hint.write_the_question_here">Write the question here</string>
<string name="textview.text.select_subject">Select subject</string>
<string name="error.message.network_error">Network error</string>
<string name="error.message.call_failed">Call failed</string>
<string name="error.message.map_loading_failed">Map loading failed</string>
```

-   Đặt tên cho hình ảnh sử dụng:

``` xml
o   icon: ic_*
o   background: bg_*
o   Image: img_*
```

-   1 số quy ước đặt tên khác:

``` xml
o   ArrayList: thêm s vào cuối từ ví dụ như: mStudents
o   Tên model: rõ ràng ví dụ như: Student, Teacher
o   Fragment: *Activity ví dụ: UserActivity
o   Activity Name: *Fragment ví dụ: HomeFragment
o   Adapter: *Adapter ví dụ PageAdapter
```
#   M u s i c P l a y e r  
 