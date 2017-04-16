import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { SystemConfiguration } from './system-configuration.model';
import { SystemConfigurationService } from './system-configuration.service';

@Component({
    selector: 'jhi-system-configuration-detail',
    templateUrl: './system-configuration-detail.component.html'
})
export class SystemConfigurationDetailComponent implements OnInit, OnDestroy {

    systemConfiguration: SystemConfiguration;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private systemConfigurationService: SystemConfigurationService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['systemConfiguration']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSystemConfigurations();
    }

    load(id) {
        this.systemConfigurationService.find(id).subscribe((systemConfiguration) => {
            this.systemConfiguration = systemConfiguration;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.systemConfiguration.id !== undefined) {
            this.systemConfigurationService.update(this.systemConfiguration)
                .subscribe((res: SystemConfiguration) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.systemConfigurationService.create(this.systemConfiguration)
                .subscribe((res: SystemConfiguration) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: SystemConfiguration) {
        this.eventManager.broadcast({ name: 'systemConfigurationModification', content: 'OK'});
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

    registerChangeInSystemConfigurations() {
        this.eventSubscriber = this.eventManager.subscribe('systemConfigurationListModification', (response) => this.load(this.systemConfiguration.id));
    }
}
