import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptionValueDescription } from './customer-option-value-description.model';
import { CustomerOptionValueDescriptionPopupService } from './customer-option-value-description-popup.service';
import { CustomerOptionValueDescriptionService } from './customer-option-value-description.service';

@Component({
    selector: 'jhi-customer-option-value-description-delete-dialog',
    templateUrl: './customer-option-value-description-delete-dialog.component.html'
})
export class CustomerOptionValueDescriptionDeleteDialogComponent {

    customerOptionValueDescription: CustomerOptionValueDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerOptionValueDescriptionService: CustomerOptionValueDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptionValueDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerOptionValueDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerOptionValueDescriptionListModification',
                content: 'Deleted an customerOptionValueDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-option-value-description-delete-popup',
    template: ''
})
export class CustomerOptionValueDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionValueDescriptionPopupService: CustomerOptionValueDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.customerOptionValueDescriptionPopupService
                .open(CustomerOptionValueDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
