import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { MerchantConfiguration } from './merchant-configuration.model';
import { MerchantConfigurationPopupService } from './merchant-configuration-popup.service';
import { MerchantConfigurationService } from './merchant-configuration.service';
import { MerchantStore, MerchantStoreService } from '../merchant-store';

@Component({
    selector: 'jhi-merchant-configuration-dialog',
    templateUrl: './merchant-configuration-dialog.component.html'
})
export class MerchantConfigurationDialogComponent implements OnInit {

    merchantConfiguration: MerchantConfiguration;
    authorities: any[];
    isSaving: boolean;

    merchantstores: MerchantStore[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private merchantConfigurationService: MerchantConfigurationService,
        private merchantStoreService: MerchantStoreService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['merchantConfiguration', 'merchantConfigurationType']);
        this.merchantConfiguration = new MerchantConfiguration();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.merchantStoreService.query().subscribe(
            (res: Response) => { this.merchantstores = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
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
        this.eventManager.broadcast({ name: 'merchantConfigurationListModification', content: 'OK'});
        this.isSaving = false;
        this.merchantConfiguration = result;
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

    trackMerchantStoreById(index: number, item: MerchantStore) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-merchant-configuration-popup',
    template: ''
})
export class MerchantConfigurationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantConfigurationPopupService: MerchantConfigurationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.merchantConfigurationPopupService
                    .open(MerchantConfigurationDialogComponent, params['id']);
            } else {
                this.modalRef = this.merchantConfigurationPopupService
                    .open(MerchantConfigurationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
