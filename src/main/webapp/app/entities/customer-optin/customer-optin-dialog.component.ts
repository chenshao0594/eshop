import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptin } from './customer-optin.model';
import { CustomerOptinPopupService } from './customer-optin-popup.service';
import { CustomerOptinService } from './customer-optin.service';
import { Optin, OptinService } from '../optin';

@Component({
    selector: 'jhi-customer-optin-dialog',
    templateUrl: './customer-optin-dialog.component.html'
})
export class CustomerOptinDialogComponent implements OnInit {

    customerOptin: CustomerOptin;
    authorities: any[];
    isSaving: boolean;

    optins: Optin[];
        constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerOptinService: CustomerOptinService,
        private optinService: OptinService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptin']);
        this.customerOptin = new CustomerOptin();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.optinService.query().subscribe(
            (res: Response) => { this.optins = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customerOptin.id !== undefined) {
            this.customerOptinService.update(this.customerOptin)
                .subscribe((res: CustomerOptin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptinService.create(this.customerOptin)
                .subscribe((res: CustomerOptin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptin) {
        this.eventManager.broadcast({ name: 'customerOptinListModification', content: 'OK'});
        this.isSaving = false;
        this.customerOptin = result;
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

    trackOptinById(index: number, item: Optin) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-optin-popup',
    template: ''
})
export class CustomerOptinPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptinPopupService: CustomerOptinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerOptinPopupService
                    .open(CustomerOptinDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerOptinPopupService
                    .open(CustomerOptinDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
