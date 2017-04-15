import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptionDescription } from './customer-option-description.model';
import { CustomerOptionDescriptionPopupService } from './customer-option-description-popup.service';
import { CustomerOptionDescriptionService } from './customer-option-description.service';

@Component({
    selector: 'jhi-customer-option-description-delete-dialog',
    templateUrl: './customer-option-description-delete-dialog.component.html'
})
export class CustomerOptionDescriptionDeleteDialogComponent {

    customerOptionDescription: CustomerOptionDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerOptionDescriptionService: CustomerOptionDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptionDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerOptionDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerOptionDescriptionListModification',
                content: 'Deleted an customerOptionDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-option-description-delete-popup',
    template: ''
})
export class CustomerOptionDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionDescriptionPopupService: CustomerOptionDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.customerOptionDescriptionPopupService
                .open(CustomerOptionDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
