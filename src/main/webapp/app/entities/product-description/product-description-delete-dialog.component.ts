import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductDescription } from './product-description.model';
import { ProductDescriptionPopupService } from './product-description-popup.service';
import { ProductDescriptionService } from './product-description.service';

@Component({
    selector: 'jhi-product-description-delete-dialog',
    templateUrl: './product-description-delete-dialog.component.html'
})
export class ProductDescriptionDeleteDialogComponent {

    productDescription: ProductDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productDescriptionService: ProductDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productDescriptionListModification',
                content: 'Deleted an productDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-description-delete-popup',
    template: ''
})
export class ProductDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productDescriptionPopupService: ProductDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productDescriptionPopupService
                .open(ProductDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
