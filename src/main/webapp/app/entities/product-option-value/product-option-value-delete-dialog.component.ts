import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductOptionValue } from './product-option-value.model';
import { ProductOptionValuePopupService } from './product-option-value-popup.service';
import { ProductOptionValueService } from './product-option-value.service';

@Component({
    selector: 'jhi-product-option-value-delete-dialog',
    templateUrl: './product-option-value-delete-dialog.component.html'
})
export class ProductOptionValueDeleteDialogComponent {

    productOptionValue: ProductOptionValue;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productOptionValueService: ProductOptionValueService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productOptionValue']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productOptionValueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productOptionValueListModification',
                content: 'Deleted an productOptionValue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-option-value-delete-popup',
    template: ''
})
export class ProductOptionValueDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productOptionValuePopupService: ProductOptionValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productOptionValuePopupService
                .open(ProductOptionValueDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
