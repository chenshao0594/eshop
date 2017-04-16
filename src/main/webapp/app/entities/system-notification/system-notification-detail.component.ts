import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { SystemNotification } from './system-notification.model';
import { SystemNotificationService } from './system-notification.service';

@Component({
    selector: 'jhi-system-notification-detail',
    templateUrl: './system-notification-detail.component.html'
})
export class SystemNotificationDetailComponent implements OnInit, OnDestroy {

    systemNotification: SystemNotification;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private systemNotificationService: SystemNotificationService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['systemNotification']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSystemNotifications();
    }

    load(id) {
        this.systemNotificationService.find(id).subscribe((systemNotification) => {
            this.systemNotification = systemNotification;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.systemNotification.id !== undefined) {
            this.systemNotificationService.update(this.systemNotification)
                .subscribe((res: SystemNotification) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.systemNotificationService.create(this.systemNotification)
                .subscribe((res: SystemNotification) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: SystemNotification) {
        this.eventManager.broadcast({ name: 'systemNotificationModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSystemNotifications() {
        this.eventSubscriber = this.eventManager.subscribe('systemNotificationListModification', (response) => this.load(this.systemNotification.id));
    }
}
