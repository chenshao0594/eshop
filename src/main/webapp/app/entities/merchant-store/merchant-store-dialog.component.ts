import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { MerchantStore } from './merchant-store.model';
import { MerchantStorePopupService } from './merchant-store-popup.service';
import { MerchantStoreService } from './merchant-store.service';
import { Language, LanguageService } from '../language';

@Component({
    selector: 'jhi-merchant-store-dialog',
    templateUrl: './merchant-store-dialog.component.html'
})
export class MerchantStoreDialogComponent implements OnInit {

    merchantStore: MerchantStore;
    authorities: any[];
    isSaving: boolean;

    languages: Language[];
        constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private merchantStoreService: MerchantStoreService,
        private languageService: LanguageService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['merchantStore']);
        this.merchantStore = new MerchantStore();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.languageService.query().subscribe(
            (res: Response) => { this.languages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.merchantStore.id !== undefined) {
            this.merchantStoreService.update(this.merchantStore)
                .subscribe((res: MerchantStore) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.merchantStoreService.create(this.merchantStore)
                .subscribe((res: MerchantStore) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: MerchantStore) {
        this.eventManager.broadcast({ name: 'merchantStoreListModification', content: 'OK'});
        this.isSaving = false;
        this.merchantStore = result;
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

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-merchant-store-popup',
    template: ''
})
export class MerchantStorePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantStorePopupService: MerchantStorePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.merchantStorePopupService
                    .open(MerchantStoreDialogComponent, params['id']);
            } else {
                this.modalRef = this.merchantStorePopupService
                    .open(MerchantStoreDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
