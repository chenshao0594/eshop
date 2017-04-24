import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { TaxClass } from './tax-class.model';
import { TaxClassPopupService } from './tax-class-popup.service';
import { TaxClassService } from './tax-class.service';
import { MerchantStore, MerchantStoreService } from '../merchant-store';

@Component({
    selector: 'jhi-tax-class-dialog',
    templateUrl: './tax-class-dialog.component.html'
})
export class TaxClassDialogComponent implements OnInit {

    taxClass: TaxClass;
    authorities: any[];
    isSaving: boolean;

    merchantstores: MerchantStore[];
            constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private taxClassService: TaxClassService,
        private merchantStoreService: MerchantStoreService,
        private eventManager: EventManager,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['taxClass']);
    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.load(params['id']);
            } else {
                this.taxClass = new TaxClass();
            }
        });
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.merchantStoreService.query().subscribe(
            (res: Response) => { this.merchantstores = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    load(id) {
         this.taxClassService.find(id).subscribe((taxClass) => {
                this.taxClass = taxClass;
                
         });
    }
    clear() {
       // this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.taxClass.id !== undefined) {
            this.taxClassService.update(this.taxClass)
                .subscribe((res: TaxClass) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.taxClassService.create(this.taxClass)
                .subscribe((res: TaxClass) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: TaxClass) {
        this.eventManager.broadcast({ name: 'taxClassListModification', content: 'OK'});
        this.isSaving = false;
      //  this.activeModal.dismiss(result);
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
    selector: 'jhi-tax-class-popup',
    template: ''
})
export class TaxClassPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taxClassPopupService: TaxClassPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.taxClassPopupService
                    .open(TaxClassDialogComponent, params['id']);
            } else {
                this.modalRef = this.taxClassPopupService
                    .open(TaxClassDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
