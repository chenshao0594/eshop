import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptionValue } from './customer-option-value.model';
import { CustomerOptionValuePopupService } from './customer-option-value-popup.service';
import { CustomerOptionValueService } from './customer-option-value.service';

@Component({
    selector: 'jhi-customer-option-value-delete-dialog',
    templateUrl: './customer-option-value-delete-dialog.component.html'
})
export class CustomerOptionValueDeleteDialogComponent {

    customerOptionValue: CustomerOptionValue;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerOptionValueService: CustomerOptionValueService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptionValue']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerOptionValueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerOptionValueListModification',
                content: 'Deleted an customerOptionValue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-option-value-delete-popup',
    template: ''
})
export class CustomerOptionValueDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionValuePopupService: CustomerOptionValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.customerOptionValuePopupService
                .open(CustomerOptionValueDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
