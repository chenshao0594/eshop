import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { IntegrationModule } from './integration-module.model';
import { IntegrationModulePopupService } from './integration-module-popup.service';
import { IntegrationModuleService } from './integration-module.service';

@Component({
    selector: 'jhi-integration-module-delete-dialog',
    templateUrl: './integration-module-delete-dialog.component.html'
})
export class IntegrationModuleDeleteDialogComponent {

    integrationModule: IntegrationModule;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private integrationModuleService: IntegrationModuleService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['integrationModule']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.integrationModuleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'integrationModuleListModification',
                content: 'Deleted an integrationModule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-integration-module-delete-popup',
    template: ''
})
export class IntegrationModuleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private integrationModulePopupService: IntegrationModulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.integrationModulePopupService
                .open(IntegrationModuleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
