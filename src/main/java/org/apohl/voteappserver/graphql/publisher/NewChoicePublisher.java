package org.apohl.voteappserver.graphql.publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;
import lombok.extern.slf4j.Slf4j;
import org.apohl.voteappserver.domain.Vote;

/**
 * Publishes update events on specific {@link Vote}s.
 */
@Slf4j
public class NewChoicePublisher {

    private final Flowable<Vote> publisher;

    private ObservableEmitter<Vote> emitter;

    public NewChoicePublisher() {
        Observable<Vote> commentUpdateObservable = Observable.create(emitter -> {
            this.emitter = emitter;
        });

        ConnectableObservable<Vote> connectableObservable = commentUpdateObservable.share().publish();
        connectableObservable.connect();

        publisher = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

    public void publish(final Vote vote) {
        log.debug("publishing new choice on vote [{}]", vote.getId());
        emitter.onNext(vote);
    }

    public Flowable<Vote> getPublisher() {
        return publisher;
    }
}
