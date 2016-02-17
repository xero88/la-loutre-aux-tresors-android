package ch.xero88.qoqa.Data;

import android.support.annotation.NonNull;

import ch.xero88.qoqa.Data.Users.ConcreteUsersRepository;
import ch.xero88.qoqa.Data.Users.UsersRepository;
import ch.xero88.qoqa.Data.Users.UsersServiceApi;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Anthony on 13/02/2016.
 */
public class QoQaRepositories {

    private static UsersRepository repository = null;

    public QoQaRepositories() {
    }

    public synchronized static UsersRepository getUserRepoInstance(@NonNull UsersServiceApi userServiceApi) {
        checkNotNull(userServiceApi);
        if (null == repository) {
            repository = new ConcreteUsersRepository(userServiceApi);
        }
        return repository;
    }
}
