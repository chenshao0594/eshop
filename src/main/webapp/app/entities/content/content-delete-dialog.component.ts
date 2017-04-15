import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Content } from './content.model';
import { ContentPopupService } from './content-popup.service';
import { ContentService } from './content.service';

@Component({
    selector: 'jhi-content-delete-dialog',
    templateUrl: './content-delete-dialog.component.html'
})
export class ContentDeleteDialogComponent {

    content: Content;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private contentService: ContentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['content', 'contentType', 'contentPosition']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contentListModification',
                content: 'Deleted an content'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-content-delete-popup',
    template: ''
})
export class ContentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contentPopupService: ContentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.contentPopupService
                .open(ContentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
