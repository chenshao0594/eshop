import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CustomerAttribute } from './customer-attribute.model';
import { CustomerAttributePopupService } from './customer-attribute-popup.service';
import { CustomerAttributeService } from './customer-attribute.service';

@Component({
    selector: 'jhi-customer-attribute-delete-dialog',
    templateUrl: './customer-attribute-delete-dialog.component.html'
})
export class CustomerAttributeDeleteDialogComponent {

    customerAttribute: CustomerAttribute;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerAttributeService: CustomerAttributeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerAttribute']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerAttributeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerAttributeListModification',
                content: 'Deleted an customerAttribute'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-attribute-delete-popup',
    template: ''
})
export class CustomerAttributeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerAttributePopupService: CustomerAttributePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.customerAttributePopupService
                .open(CustomerAttributeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
