# NB Stocks
NB Stocks is a stock trading application. You can manage your balance, track stock prices in realtime,
and ease your life. NB Stocks is a unique app as it provides trade buy/sell functionality,
has a built-in chart based on stock price changes, and provides real-time stock
information, such as maximum and minimum value in 24 hours, monthly price change, latest trade history and so on.
  #


## 🚀 Developers

- [Bachana Mosulishvili](https://www.github.com/bchmsl)

- [Nika Namoradze](https://github.com/NikkaNamoradze)

We are beginner Android developers,
who study at [Business and Technology University](https://btu.edu.ge/). We studied
Android development at first at University and then
[TBC IT Academy](https://www.tbcacademy.ge/it-academy) course came up and changed our lives. 
 ## 
## 📝 Technical documentation

Upon logging in for the first time, a user sees a login screen,
where they can choose whether they want to log in or sign up.
After user logs into their account, they are on their home screen.
The application has *Bottom Navigation Menu* so that users
can easily navigate between fragments, such as the Home screen, *companies listing*, and user profile screen.

  
### Home
On the home screen, users can clearly see their card with balance on it.
Right side of the card, there is a deposit button, where they can *simulate depositing money*. They
can enter any amount and it will be added to their balance, likewise they can *simulate withdrawal money*.
User We will also meet wachlist, stock ownership and latest trade transactions

  

### Companies listing
On companies listing they can search desired stock or look through a lot of stocks.
  
  
### Details page
On details page user observe stock current price, day high, day low and price chart which stands on weekly and monthly 
stock open and close exchange rate. Also on this page user can add stock in watchlist, or buy/sell it.
  
  
### Profile page
On the profile screen user sees information about themselves. They can change profile picture
using URI, control balance visibility, change password, see information about the application and sign out.


  
# 
## 🤓 Documentation for geeks 🙂
NB Stocks application uses *Kotlin* as its base language and standard Android
SDK with *XML layouts* and *MVVM* with *clean architecture*. The application follows all **SOLID
Principles** and uses *single activity architecture*. GB Bank application uses [Dagger Hilt](https://github.com/google/dagger/tree/master/java/dagger/hilt)
for *dependency injection* and [Retrofit2](https://github.com/square/retrofit) for *API requests*.
  

NB Stocks application uses [Google's Firebase](https://firebase.google.com/) for most of its parts.
In order to store some local data application uses Room database. 
It uses *Firebase Authentication* and *Realtime Database* features from *Google*.
App also uses *Yahoo finance Api* for [Stock Information](https://finance.yahoo.com/)
