package org.example.a24_12_sparks.services;

import org.example.a24_12_sparks.model.UserBean;

import java.util.ArrayList;
import java.util.Objects;

public class UserService {

    private static final ArrayList<UserBean> list = new ArrayList<>();
    private static long idNumber = 1;

//Jeu de donnée si besoin
//    static {
//        list.add(new UserBean(1, "toto", "aaa"));
//        list.add(new UserBean(2, "tata", "bbb"));
//    }

    //Sauvegarde Create or Update
    public static UserBean save(UserBean user) {
        //On regarde s'il n'est pas déjà en base
        UserBean userRegister = findById(user.getId());
        if (userRegister != null) {
            //on retire celui en base pour remplacer par celui la
            list.remove(userRegister);
        } else {
            //on ajoute un id généré
            user.setId(idNumber++);
        }
        list.add(user);
        return user;
    }

    //Retourne la liste
    public static ArrayList<UserBean> load() {
        return list;
    }

    //Permet de trouver l'utilisateur qui utilise cette session
    public static UserBean findById(Long id) {
        if (id != null) {
            for (UserBean userBean : list) {
                if (Objects.equals(userBean.getId(), id)) {
                    return userBean;
                }
            }
        }
        //Pas d'utilisateur qui a cette session
        return null;
    }

    //Supprime l'élément.Retourne true si la liste a changé
    public static boolean deleteById(Long id) {
        return list.removeIf(user -> Objects.equals(user.getId(), id));
    }

}
