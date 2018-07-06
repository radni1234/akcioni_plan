/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AkcioniPlanTestModule } from '../../../test.module';
import { KriterijumBodovanjeDeleteDialogComponent } from 'app/entities/kriterijum-bodovanje/kriterijum-bodovanje-delete-dialog.component';
import { KriterijumBodovanjeService } from 'app/entities/kriterijum-bodovanje/kriterijum-bodovanje.service';

describe('Component Tests', () => {
    describe('KriterijumBodovanje Management Delete Component', () => {
        let comp: KriterijumBodovanjeDeleteDialogComponent;
        let fixture: ComponentFixture<KriterijumBodovanjeDeleteDialogComponent>;
        let service: KriterijumBodovanjeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [KriterijumBodovanjeDeleteDialogComponent]
            })
                .overrideTemplate(KriterijumBodovanjeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KriterijumBodovanjeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KriterijumBodovanjeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
