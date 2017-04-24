import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

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

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private emailSettingService: EmailSettingService,
        private route: ActivatedRoute
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmailSettings() {
        this.eventSubscriber = this.eventManager.subscribe('emailSettingListModification', (response) => this.load(this.emailSetting.id));
    }
}
