import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Optin } from './optin.model';
import { OptinPopupService } from './optin-popup.service';
import { OptinService } from './optin.service';

@Component({
    selector: 'jhi-optin-dialog',
    templateUrl: './optin-dialog.component.html'
})
export class OptinDialogComponent implements OnInit {

    optin: Optin;
    authorities: any[];
    isSaving: boolean;
            constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private optinService: OptinService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['optin']);
        this.optin = new Optin();
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
        if (this.optin.id !== undefined) {
            this.optinService.update(this.optin)
                .subscribe((res: Optin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.optinService.create(this.optin)
                .subscribe((res: Optin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Optin) {
        this.eventManager.broadcast({ name: 'optinListModification', content: 'OK'});
        this.isSaving = false;
        this.optin = result;
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
    selector: 'jhi-optin-popup',
    template: ''
})
export class OptinPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private optinPopupService: OptinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.optinPopupService
                    .open(OptinDialogComponent, params['id']);
            } else {
                this.modalRef = this.optinPopupService
                    .open(OptinDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
