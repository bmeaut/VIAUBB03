# Labor 02 - Felhasználói felület készítése - Fragmentek, Chartok

## Bevezető

A labor során egy HR alkalmazást készítünk el, amelybe belépve a felhasználó meg tudja tekinteni személyes adatait, illetve szabadságot tud rögzíteni. Az alkalmazás nem használ perzisztens adattárolást és valós bejelentkeztetést, csak demo adatokkal dolgozik. A labor fő témája a Fragmentekkel való felületkészítés lesz.

<p align="center">
<img src="./assets/menu.png" width="160">
<img src="./assets/profile1.png" width="160">
<img src="./assets/profile2.png" width="160">
<img src="./assets/holiday1.png" width="160">
<img src="./assets/datepicker.png" width="160">
</p>

## Értékelés

Vezetett rész (1 pont)
- [Projekt létrehozása](#projekt-létrehozása)
- [Főmenü képernyő](#főmenü-képernyő)
- [Profil képernyő](#profil-képernyő)
- [Szabadság képernyő](#szabadság-képernyő)
- [Dátumválasztó, napok csökkentése](#dátumválasztó-napok-csökkentése)

Önálló feladat (1 pont)
- [Szabadság továbbfejlesztése](#szabadság-továbbfejlesztése)

Bónusz feladatok
- [Fizetés menüpont megvalósítása](#Fizetés-menüpont-megvalósítása)

## Vezetett rész

### Projekt létrehozása

Első lépésként indítsuk el az Android Studio-t, majd:

1. Hozzunk létre egy új projektet, válasszuk az *Add No Activity* lehetőséget.
2. A projekt neve legyen `WorkplaceApp`, a kezdő package pedig `hu.bme.aut.workplaceapp`
3. Nyelvnek válasszuk a *Kotlin*-t.
4. A minimum API szint legyen API21: Android 5.0.
5. Az instant app támogatást, valamint a *Use legacy android.support libraries* pontot **ne** pipáljuk be.

Az első Activity-nk legyen egy Empty Activity, és nevezzük el `MenuActivity`-nek (app-on jobb gomb, New -> Activity -> Empty Activity). A hozzá tartozó layout fájl automatikusan megkapja az `activity_menu.xml` nevet.

Előzetesen töltsük le az alkalmazás képeit tartalmazó [tömörített fájlt](./downloads/res.zip) és bontsuk ki. A benne lévő drawable könyvtárat másoljuk be az app/src/main/res mappába (Studio-ban res mappán állva `Ctrl+V`).

### Főmenü képernyő

Az első Activity amit elkészítünk a navigációért lesz felelős. A labor során 2 funkciót fogunk megvalósítani, ezek a Profil és a Szabadság.

A projekt készítésekor már létrejött `activity_menu.xml` tartalmát cseréljük ki az alábbira:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="16dp"
    android:gravity="center"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <FrameLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1">

        </FrameLayout>

        <FrameLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1">

        </FrameLayout>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <FrameLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1">

        </FrameLayout>

        <FrameLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1">

        </FrameLayout>

    </LinearLayout>

</LinearLayout>
```

Egy függőleges LinearLayout-ba tettünk bele 2 vízszintes LinearLayout-ot, mindkettő 2 gombot fog tartalmazni. Súlyozás segítségével 2 részre osztottuk a vízszintes LinearLayout-okat.
A gombon a háttér és a felirat elhelyezéséhez a korábbi laboron már látotthoz hasonlóan FrameLayout-ot fogunk használni.

Az első gombot például így készíthetjük el (a `FrameLayout` tagbe írjuk):
```xml
<ImageButton
    android:id="@+id/btnProfile"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:adjustViewBounds="true"
    android:scaleType="fitCenter"
    android:src="@drawable/profile" />

<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    android:text="Profile"
    android:textSize="34sp" />
```

A további 3 gombot ennek a mintájára készítsük el ezekkel az értékekkel:

| Szöveg | ID | Kép |
| -- | -- | -- |
| Holiday | `@+id/btnHoliday` | `@drawable/holiday` |
| Payment | `@+id/btnPayment` | `@drawable/payment` |
| Cafeteria | `@+id/btnCafeteria` | `@drawable/cafeteria` |

Ne felejtsük el a szövegeket kiszervezni erőforrásba! (a szövegen állva `Alt+Enter`)

Hozzunk létre a két új Empty Activity-t (`ProfileActivity` és `HolidayActivity`)

A MenuActivity fájljában (`MenuActivity.kt`) rendeljünk a gombok lenyomásához eseménykezelőt az onCreate metódusban:

```kotlin
btnProfile.setOnClickListener {
            val profileIntent = Intent(this@MenuActivity, ProfileActivity::class.java)
            startActivity(profileIntent)
        }
btnHoliday.setOnClickListener {
            val holidayIntent = Intent(this@MenuActivity, HolidayActivity::class.java)
            startActivity(holidayIntent)
        } });
```

Mivel az Activityt kézzel hoztuk létre, így az első futtatás előtt meg kell adnunk az `AndroidManifest.xml` file-ban, hogy mi legyen az alkalmazás belépési pontja.

```xml
…
<activity android:name=".MenuActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
</activity>
```

Próbáljuk ki az alkalmazást! 4 gombnak kell megjelennie és a felső kettőn működnie kell a navigációnak a (még) üres Activity-kbe.

### Profil képernyő

A Profil képernyő két lapozható oldalból fog állni, ezen a név, email, lakcím (első oldal), illetve a személyigazolvány szám, TAJ szám, adószám és törzsszám (második oldal) fognak megjelenni.

Hozzunk létre egy `data` package-et, azon belül egy `Person` osztályt, ebben fogjuk tárolni az oldalakon megjelenő adatokat:

```kotlin
class Person(
    val name: String,
    val email: String,
    val address: String,
    val id: String,
    val socialSecurityNumber: String,
    val taxId: String,
    val registrationId: String
)
```

A Person osztály példányának elérésére hozzunk létre egy `DataManager` osztályt (szintén a `data` package-en belül), ezzel fogjuk szimulálni a valós adatelérést. Ehhez a Singleton mintát használunk, hogy az alkalmazás minden részéből egyszerűen elérhető legyen. A Person példányt rögtön létre is hozzuk benne.

> Kotlinban nyelvi szintű támogatás van a singletonok létrehozására. Ahelyett, hogy nekünk kéne egyetlen statikus példányt felvennünk, elég csak a `class` kulcsszó helyett az [`object`](https://kotlinlang.org/docs/reference/object-declarations.html#object-declarations) kulcsszóval létrehoznunk az osztályt hogy egy singletont kapjunk.

```kotlin
object DataManager {
    val person: Person = Person(
        "Test User", "testuser@domain.com",
        "1234 Test, Random Street 1.",
        "123456AB",
        "123456789",
        "1234567890",
        "0123456789"
    )
}
```

Ezután elkészíthetjük a két oldalt, Fragmentekkel. Hozzuk létre egy új `fragments` package-ben a két Fragmentet (New -> Kotlin File/Class -> Kind: Class), ezek neve legyen `MainProfileFragment` és `DetailsProfileFragment`.

A két Fragmentben származzunk le a Fragment osztályból (androidx-es verziót válasszuk) és definiáljuk felül az onCreateView metódust. Ebben betöltjük a layout-ot és a Person objektum adatait kiírjuk a TextView-kra.

`MainProfileFragment.kt`:
```kotlin
class MainProfileFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(aut.bme.hu.workplaceapp.R.layout.profile_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val person: Person = DataManager.person

        tvName.text = person.name
        tvEmail.text = person.email
        tvAddress.text = person.address

    }
}
```

`DetailsProfileFragment.kt`:
```kotlin
class DetailsProfileFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_detail, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val person: Person = DataManager.person

        tvId.text = person.id
        tvSSN.text = person.socialSecurityNumber
        tvTaxId.text = person.taxId
        tvRegistrationId.text = person.registrationId
    }
}
```

Készítsük el a megfelelő layout-okat a Fragmentekhez (`R.layout.profile_main` és `R.layout.profile_detail`-en állva `Alt+Enter`).

`profile_main.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Name:"
        android:textAllCaps="true"
        android:textSize="20sp" />

    <TextView
        android:id="@+id/tvName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="20dp"
        android:textColor="#000000"
        android:textSize="34sp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Email:"
        android:textAllCaps="true"
        android:textSize="20sp" />

    <TextView
        android:id="@+id/tvEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="20dp"
        android:textColor="#000000"
        android:textSize="34sp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Address:"
        android:textAllCaps="true"
        android:textSize="20sp" />

    <TextView
        android:id="@+id/tvAddress"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="20dp"
        android:textColor="#000000"
        android:textSize="34sp" />

</LinearLayout>
```

`profile_detail.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="ID:"
        android:textAllCaps="true"
        android:textSize="20sp" />

    <TextView
        android:id="@+id/tvId"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="20dp"
        android:textColor="#000000"
        android:textSize="34sp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Social Security ID:"
        android:textAllCaps="true"
        android:textSize="20sp" />

    <TextView
        android:id="@+id/tvSSN"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="20dp"
        android:textColor="#000000"
        android:textSize="34sp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Tax ID:"
        android:textAllCaps="true"
        android:textSize="20sp" />

    <TextView
        android:id="@+id/tvTaxId"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="20dp"
        android:textColor="#000000"
        android:textSize="34sp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Registration ID:"
        android:textAllCaps="true"
        android:textSize="20sp" />

    <TextView
        android:id="@+id/tvRegistrationId"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="20dp"
        android:textColor="#000000"
        android:textSize="34sp" />

</LinearLayout>
```

(Szervezzük ki a szövegeket erőforrásba)

Már csak a lapozás megvalósítása maradt hátra, ezt a ViewPager osztállyal fogjuk megvalósítani.

Az `activity_profile.xml` fájlba hozzunk létre egy `ViewPager`-t:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="aut.bme.hu.workplaceapp.ProfileActivity">

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/vpProfile"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```

A ViewPager osztály egy PagerAdapter osztály segítségével tudja az oldalakat létrehozni. Hozzunk létre egy új `adapter` package-be egy ProfilePagerAdaptert a két Fragmentünkhöz. 

`ProfilePagerAdapter.kt`:
```kotlin
class ProfilePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        private const val NUM_PAGES = 2
    }
    
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MainProfileFragment()
            1 -> DetailsProfileFragment()
            else -> MainProfileFragment()
        }
    }

    override fun getCount(): Int = NUM_PAGES
}
```

A ProfileActivity-ben rendeljük hozzá a ViewPagerhez a most elkészített adaptert (onCreate metódus): 
```kotlin
vpProfile.adapter = ProfilePagerAdapter(supportFragmentManager)
```

Próbáljuk ki az alkalmazást. A Profile gombra kattinva megjelennek a felhasználó adatai és lehet lapozni is.

### Szabadság képernyő

A Szabadság képernyőn egy kördiagramot fogunk megjeleníteni, ami mutatja, hogy mennyi szabadságot vettünk már ki és mennyi maradt. Ezen kívül egy gomb segítségével új szabadnap kivételét is megengedjük a felhasználónak.

Először egészítsük ki a DataManager osztályunkat, hogy kezelje a szabadsághoz kapcsolódó adatokat is:


### Dátumválasztó, napok csökkentése

## Önálló feladat
### Szabadság továbbfejlesztése

## Bónusz feladatok
### Fizetés menüpont megvalósítása
