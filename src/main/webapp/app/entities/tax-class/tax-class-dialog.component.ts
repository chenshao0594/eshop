import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { TaxClass } from './tax-class.model';
import { TaxClassPopupService } from './tax-class-popup.service';
import { TaxClassService } from './tax-class.service';

@Component({
    selector: 'jhi-tax-class-dialog',
    templateUrl: './tax-class-dialog.component.html'
})
export class TaxClassDialogComponent implements OnInit {

    taxClass: TaxClass;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private taxClassService: TaxClassService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['taxClass']);
        this.taxClass = new TaxClass();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        window.history.back();
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
        this.taxClass = result;
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