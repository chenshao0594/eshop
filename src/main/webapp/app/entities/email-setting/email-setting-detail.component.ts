import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { EmailSetting } from './email-setting.model';
import { EmailSettingService } from './email-setting.service';

@Component({
    selector: 'jhi-email-setting-detail',
    templateUrl: './email-setting-detail.component.html'
})
export class EmailSettingDetailComponent implements OnInit, OnDestroy {

    emailSetting: EmailSetting;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private emailSettingService: EmailSettingService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['emailSetting', 'sMTPSecurityEnum']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmailSettings();
    }

    load(id) {
        this.emailSettingService.find(id).subscribe((emailSetting) => {
            this.emailSetting = emailSetting;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.emailSetting.id !== undefined) {
            this.emailSettingService.update(this.emailSetting)
                .subscribe((res: EmailSetting) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.emailSettingService.create(this.emailSetting)
                .subscribe((res: EmailSetting) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: EmailSetting) {
        this.eventManager.broadcast({ name: 'emailSettingModification', content: 'OK'});
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

    registerChangeInEmailSettings() {
        this.eventSubscriber = this.eventManager.subscribe('emailSettingListModification', (response) => this.load(this.emailSetting.id));
    }
}
