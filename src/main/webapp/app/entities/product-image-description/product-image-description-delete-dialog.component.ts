import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductImageDescription } from './product-image-description.model';
import { ProductImageDescriptionPopupService } from './product-image-description-popup.service';
import { ProductImageDescriptionService } from './product-image-description.service';

@Component({
    selector: 'jhi-product-image-description-delete-dialog',
    templateUrl: './product-image-description-delete-dialog.component.html'
})
export class ProductImageDescriptionDeleteDialogComponent {

    productImageDescription: ProductImageDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productImageDescriptionService: ProductImageDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productImageDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productImageDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productImageDescriptionListModification',
                content: 'Deleted an productImageDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-image-description-delete-popup',
    template: ''
})
export class ProductImageDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productImageDescriptionPopupService: ProductImageDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productImageDescriptionPopupService
                .open(ProductImageDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
