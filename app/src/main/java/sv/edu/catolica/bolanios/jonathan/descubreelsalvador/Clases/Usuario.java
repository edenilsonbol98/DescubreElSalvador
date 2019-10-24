package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable{
    private String email;
    private String user_id;
    private String username;
    private String password;

    public Usuario(String email, String user_id, String username, String password) {
        this.email = email;
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }
    protected Usuario(Parcel in) {
        email = in.readString();
        user_id = in.readString();
        username = in.readString();
        password = in.readString();
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(user_id);
        dest.writeString(username);
        dest.writeString(password);
    }

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static Creator<Usuario> getCREATOR() {
        return CREATOR;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
