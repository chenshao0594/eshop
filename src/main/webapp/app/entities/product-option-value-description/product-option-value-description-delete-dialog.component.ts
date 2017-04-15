import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductOptionValueDescription } from './product-option-value-description.model';
import { ProductOptionValueDescriptionPopupService } from './product-option-value-description-popup.service';
import { ProductOptionValueDescriptionService } from './product-option-value-description.service';

@Component({
    selector: 'jhi-product-option-value-description-delete-dialog',
    templateUrl: './product-option-value-description-delete-dialog.component.html'
})
export class ProductOptionValueDescriptionDeleteDialogComponent {

    productOptionValueDescription: ProductOptionValueDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productOptionValueDescriptionService: ProductOptionValueDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productOptionValueDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productOptionValueDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productOptionValueDescriptionListModification',
                content: 'Deleted an productOptionValueDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-option-value-description-delete-popup',
    template: ''
})
export class ProductOptionValueDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productOptionValueDescriptionPopupService: ProductOptionValueDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productOptionValueDescriptionPopupService
                .open(ProductOptionValueDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
