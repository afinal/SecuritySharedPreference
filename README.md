# SecuritySharedPreference
Android Security SharedPreference（Android安全的SharedPreference存储方式）欢迎Star，我的博客：http://blog.csdn.net/voidmain_123/article/details/53338393

## 前言 ##
   安全问题长久以来就是Android系统的一大弊病，很多人也因此舍弃Android选择了苹果，作为一个Android Developer，我们需要对用户的隐私负责，需要对用户的数据安全倾尽全力，想到这里，我就热血沸腾，仿佛自己化身正义的天使（我编不下去了。。。）。

## 概述 ##
现在，我们回归正题，SharedPreference是我们比较常用的保存数据到本地的方式，我们习惯在SharedPreference中保存用户信息，用户偏好设置，以及记住用户名密码等等。但是，SharedPreference存在一些安全隐患，我们都知道，SharedPreference是以“键值对”的形式把数据保存在data/data/packageName/shared_prefs文件夹中的xml文件中。
	在正常的情况下，我们没办法访问data/data目录，我们也没办法拿到xml中的文件。但是，我们root手机之后，通过命令行可以获得读写data/data目录的权限，我们SharedPreference中保存的数据也就很容易泄漏，造成不可挽回的损失。那么我们今天就从SharedPreference开刀，为APP的安全尽一份绵薄之力。

这样我们基本上实现了SharedPreference的加解密存储，APP的数据安全进一步得到了保证，现在和大家说一下使用方式：首先我们看一下普通的SharedPreference：
```
/**
 * 以常规的SharedPreference保存数据
 */
private void saveInCommonPreference(){
    SharedPreferences sharedPreferences = getSharedPreferences("common_prefs", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("username", mEmailView.getText().toString());
    editor.putString("password", mPasswordView.getText().toString());
    editor.apply();
}
```
我们看一下本地保存的效果
```
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="username">1136138123@qq.com</string>
    <string name="password">147258369</string>
</map>
```
其次，我们来看一下SecuritySharedPreference的使用方式：
```
/**
 * 以加密的SharedPreference保存数据
 */
private void saveInSecurityPreference(){
    SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(getApplicationContext(), "security_prefs", Context.MODE_PRIVATE);
    SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
    securityEditor.putString("username", mEmailView.getText().toString());
    securityEditor.putString("password", mPasswordView.getText().toString());
    securityEditor.apply();
}
```
我们看一下本地保存的效果有什么区别
```
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="BZFXj2GNc39n80SizhqRug==">Rnfpxffj9rNl29dsoQxlUzpSaR9m5K6myIYtqQOiIRU=</string>
    <string name="qF87qMi9YiXtVcIzaHOXrA==">HoHo+CFJrXK3CPMUpcTTow==</string>
</map>

```
效果非常棒！我们通过简简单单的两个类，实现了SharedPreference的加密。虽然只是个非常简单的小功能，但是给数据安全提供了护盾，O(∩_∩)O哈哈~
	有的同学可能会说，我的项目中已经使用了SharedPreference，如何迁移到SecuritySharedPreference呢？如何进行不加密数据到加密数据的过渡呢？这一点其实我已经替大家做好了，我们在下一次升级应用的时候，在第一次使用SharedPreference时，调用handleTransition()方法进行数据加密的过渡。
