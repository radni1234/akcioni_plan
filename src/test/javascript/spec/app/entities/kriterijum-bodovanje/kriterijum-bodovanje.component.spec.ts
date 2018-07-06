/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AkcioniPlanTestModule } from '../../../test.module';
import { KriterijumBodovanjeComponent } from 'app/entities/kriterijum-bodovanje/kriterijum-bodovanje.component';
import { KriterijumBodovanjeService } from 'app/entities/kriterijum-bodovanje/kriterijum-bodovanje.service';
import { KriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';

describe('Component Tests', () => {
    describe('KriterijumBodovanje Management Component', () => {
        let comp: KriterijumBodovanjeComponent;
        let fixture: ComponentFixture<KriterijumBodovanjeComponent>;
        let service: KriterijumBodovanjeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [KriterijumBodovanjeComponent],
                providers: []
            })
                .overrideTemplate(KriterijumBodovanjeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KriterijumBodovanjeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KriterijumBodovanjeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new KriterijumBodovanje(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.kriterijumBodovanjes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
