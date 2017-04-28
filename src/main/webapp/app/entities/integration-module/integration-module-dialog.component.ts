import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { IntegrationModule } from './integration-module.model';
import { IntegrationModulePopupService } from './integration-module-popup.service';
import { IntegrationModuleService } from './integration-module.service';

@Component({
    selector: 'jhi-integration-module-dialog',
    templateUrl: './integration-module-dialog.component.html'
})
export class IntegrationModuleDialogComponent implements OnInit {

    integrationModule: IntegrationModule;
    authorities: any[];
    isSaving: boolean;
            constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private integrationModuleService: IntegrationModuleService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['integrationModule']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.integrationModule.id !== undefined) {
            this.integrationModuleService.update(this.integrationModule)
                .subscribe((res: IntegrationModule) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.integrationModuleService.create(this.integrationModule)
                .subscribe((res: IntegrationModule) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: IntegrationModule) {
        this.eventManager.broadcast({ name: 'integrationModuleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
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
    selector: 'jhi-integration-module-popup',
    template: ''
})
export class IntegrationModulePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private integrationModulePopupService: IntegrationModulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.integrationModulePopupService
                    .open(IntegrationModuleDialogComponent, params['id']);
            } else {
                this.modalRef = this.integrationModulePopupService
                    .open(IntegrationModuleDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
