import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ShippingOrigin } from './shipping-origin.model';
import { ShippingOriginPopupService } from './shipping-origin-popup.service';
import { ShippingOriginService } from './shipping-origin.service';

@Component({
    selector: 'jhi-shipping-origin-dialog',
    templateUrl: './shipping-origin-dialog.component.html'
})
export class ShippingOriginDialogComponent implements OnInit {

    shippingOrigin: ShippingOrigin;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private shippingOriginService: ShippingOriginService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['shippingOrigin']);
        this.shippingOrigin = new ShippingOrigin();
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
        if (this.shippingOrigin.id !== undefined) {
            this.shippingOriginService.update(this.shippingOrigin)
                .subscribe((res: ShippingOrigin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.shippingOriginService.create(this.shippingOrigin)
                .subscribe((res: ShippingOrigin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ShippingOrigin) {
        this.eventManager.broadcast({ name: 'shippingOriginListModification', content: 'OK'});
        this.isSaving = false;
        this.shippingOrigin = result;
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
    selector: 'jhi-shipping-origin-popup',
    template: ''
})
export class ShippingOriginPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shippingOriginPopupService: ShippingOriginPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.shippingOriginPopupService
                    .open(ShippingOriginDialogComponent, params['id']);
            } else {
                this.modalRef = this.shippingOriginPopupService
                    .open(ShippingOriginDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
