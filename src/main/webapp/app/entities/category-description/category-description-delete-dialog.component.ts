import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CategoryDescription } from './category-description.model';
import { CategoryDescriptionPopupService } from './category-description-popup.service';
import { CategoryDescriptionService } from './category-description.service';

@Component({
    selector: 'jhi-category-description-delete-dialog',
    templateUrl: './category-description-delete-dialog.component.html'
})
export class CategoryDescriptionDeleteDialogComponent {

    categoryDescription: CategoryDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private categoryDescriptionService: CategoryDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['categoryDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categoryDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'categoryDescriptionListModification',
                content: 'Deleted an categoryDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-category-description-delete-popup',
    template: ''
})
export class CategoryDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categoryDescriptionPopupService: CategoryDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.categoryDescriptionPopupService
                .open(CategoryDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
