import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { MerchantConfiguration } from './merchant-configuration.model';
import { MerchantConfigurationService } from './merchant-configuration.service';

@Component({
    selector: 'jhi-merchant-configuration-detail',
    templateUrl: './merchant-configuration-detail.component.html'
})
export class MerchantConfigurationDetailComponent implements OnInit, OnDestroy {

    merchantConfiguration: MerchantConfiguration;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private merchantConfigurationService: MerchantConfigurationService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['merchantConfiguration', 'merchantConfigurationType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMerchantConfigurations();
    }

    load(id) {
        this.merchantConfigurationService.find(id).subscribe((merchantConfiguration) => {
            this.merchantConfiguration = merchantConfiguration;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.merchantConfiguration.id !== undefined) {
            this.merchantConfigurationService.update(this.merchantConfiguration)
                .subscribe((res: MerchantConfiguration) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.merchantConfigurationService.create(this.merchantConfiguration)
                .subscribe((res: MerchantConfiguration) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: MerchantConfiguration) {
        this.eventManager.broadcast({ name: 'merchantConfigurationModification', content: 'OK'});
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

    registerChangeInMerchantConfigurations() {
        this.eventSubscriber = this.eventManager.subscribe('merchantConfigurationListModification', (response) => this.load(this.merchantConfiguration.id));
    }
}
